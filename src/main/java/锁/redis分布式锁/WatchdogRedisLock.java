package 锁.redis分布式锁;

import cn.hutool.core.lang.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2025年 10月 15日 15:43
 * @Description:
 * @Author: MR.YU
 */
public class WatchdogRedisLock {

    private final JedisPool jedisPool;
    private final String lockKey;
    private final int expireTime;
    private final String lockValue;
    private volatile boolean locked = false;
    private Thread watchdogThread;

    public WatchdogRedisLock(String redisHost, int redisPort, String lockKey, int expireTime) {
        this.lockKey = lockKey;
        this.expireTime = expireTime;
        this.lockValue = UUID.randomUUID().toString();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
    }

    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long timeout = unit.toMillis(waitTime);

        try (Jedis jedis = jedisPool.getResource()) {
            while (System.currentTimeMillis() - startTime < timeout) {
                SetParams params = SetParams.setParams().nx().ex(expireTime);
                String result = jedis.set(lockKey, lockValue, params);
                if ("OK".equals(result)) {
                    locked = true;
                    startWatchdog();
                    return true;
                }
                Thread.sleep(100);
            }
        }
        return false;
    }

    private void startWatchdog() {
        watchdogThread = new Thread(() -> {
            while (locked) {
                try {
                    // 在锁过期前1/3时间进行续期
                    Thread.sleep(expireTime * 1000 / 3 * 2);

                    if (locked) {
                        try (Jedis jedis = jedisPool.getResource()) {
                            String luaScript =
                                    "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                                            "   return redis.call('expire', KEYS[1], ARGV[2]) " +
                                            "else " +
                                            "   return 0 " +
                                            "end";

                            Object result = jedis.eval(luaScript, 1, lockKey, lockValue, String.valueOf(expireTime));
                            if (Long.valueOf(0).equals(result)) {
                                // 锁已丢失，停止续期
                                locked = false;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    // 续期失败，可能是Redis连接问题
                    System.err.println("锁续期失败: " + e.getMessage());
                }
            }
        });
        watchdogThread.setDaemon(true);
        watchdogThread.start();
    }

    public boolean releaseLock() {
        locked = false;
        if (watchdogThread != null) {
            watchdogThread.interrupt();
        }

        try (Jedis jedis = jedisPool.getResource()) {
            String luaScript =
                    "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "   return redis.call('del', KEYS[1]) " +
                            "else " +
                            "   return 0 " +
                            "end";

            Object result = jedis.eval(luaScript, 1, lockKey, lockValue);
            return Long.valueOf(1).equals(result);
        }
    }
}
