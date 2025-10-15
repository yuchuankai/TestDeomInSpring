package 锁.redis分布式锁;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2025年 10月 15日 14:56
 * @Description:
 * @Author: MR.YU
 */
public class ImprovedRedisLock implements RedisLock {

    private final JedisPool jedisPool;

    private final String lockKey;

    private final int expireTime;

    private final String lockValue;

    private static final long waitTime = 1000;

    private static final TimeUnit unit = TimeUnit.MILLISECONDS;

    /**
     * SETNX + EXPIRE + 唯一值验证
     * SimpleRedisLock的升级款
     */

    public ImprovedRedisLock(String redisHost, int redisPort, String lockKey, int expireTime) {

        this.lockKey = lockKey;
        this.expireTime = expireTime;

        this.lockValue = String.valueOf(System.currentTimeMillis() + expireTime * 1000L);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);

        jedisPool = new JedisPool(config, redisHost, redisPort, null, "1qaz!QAZ123,,");
    }




    public boolean acquireLock() {

        long startTime = System.currentTimeMillis();
        long timeout = unit.toMillis(waitTime);

        try (Jedis jedis = jedisPool.getResource()) {
            while (System.currentTimeMillis() - startTime < timeout) {
                SetParams params = new SetParams().nx().ex(expireTime);
                String result = jedis.set(lockKey, lockValue, params);

                if ("OK".equals(result)) {
                    return true;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }



    /**
     * 释放锁 - 使用Lua脚本保证原子性
     */
    public boolean releaseLock() {
        try (Jedis jedis = jedisPool.getResource()) {
            // Lua脚本，只有锁的值匹配时才删除
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
