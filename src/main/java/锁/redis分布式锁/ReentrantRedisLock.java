package 锁.redis分布式锁;

import cn.hutool.core.lang.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2025年 10月 15日 15:42
 * @Description:
 * @Author: MR.YU
 */
public class ReentrantRedisLock {

    private final JedisPool jedisPool;
    private final String lockKey;
    private final int expireTime;
    private final ThreadLocal<String> lockValueHolder = new ThreadLocal<>();
    private final ThreadLocal<Integer> holdCount = ThreadLocal.withInitial(() -> 0);

    public ReentrantRedisLock(String redisHost, int redisPort, String lockKey, int expireTime) {
        this.lockKey = lockKey;
        this.expireTime = expireTime;

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
    }

    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
        // 重入检查
        if (isHeldByCurrentThread()) {
            holdCount.set(holdCount.get() + 1);
            return true;
        }

        String currentLockValue = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        long timeout = unit.toMillis(waitTime);

        try (Jedis jedis = jedisPool.getResource()) {
            while (System.currentTimeMillis() - startTime < timeout) {
                SetParams params = SetParams.setParams().nx().ex(expireTime);
                String result = jedis.set(lockKey, currentLockValue, params);
                if ("OK".equals(result)) {
                    lockValueHolder.set(currentLockValue);
                    holdCount.set(1);
                    return true;
                }
                Thread.sleep(100);
            }
        }
        return false;
    }

    public boolean releaseLock() {
        if (!isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException("当前线程不持有锁");
        }

        int count = holdCount.get() - 1;
        holdCount.set(count);

        if (count > 0) {
            return true;
        }

        // 完全释放锁
        try (Jedis jedis = jedisPool.getResource()) {
            String luaScript =
                    "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "   return redis.call('del', KEYS[1]) " +
                            "else " +
                            "   return 0 " +
                            "end";

            Object result = jedis.eval(luaScript, 1, lockKey, lockValueHolder.get());
            boolean success = Long.valueOf(1).equals(result);

            if (success) {
                lockValueHolder.remove();
                holdCount.remove();
            }
            return success;
        }
    }

    public boolean isHeldByCurrentThread() {
        return holdCount.get() > 0;
    }
}
