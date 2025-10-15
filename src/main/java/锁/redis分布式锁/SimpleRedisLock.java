package 锁.redis分布式锁;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @CreateTime: 2025年 10月 15日 13:46
 * @Description:
 * @Author: MR.YU
 */
public class SimpleRedisLock implements RedisLock {

    private final JedisPool jedisPool;

    private final String lockKey;

    /**
     * Redis分布式锁基于SETNX（SET if Not eXists）命令实现，确保在分布式环境下同一时刻只有一个客户端能获取到锁。
     * 如果获取锁后客户端崩溃，锁永远不会释放
     * 没有锁过期机制
     * 可能误删其他客户端的锁
     */

    public SimpleRedisLock(String redisHost, int redisPort, String lockKey) {

        this.lockKey = lockKey;

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);

        jedisPool = new JedisPool(config, redisHost, redisPort, null, "1qaz!QAZ123,,");
    }

    public boolean acquireLock() {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.setnx(lockKey, "1") == 1;
        }
    }


    public boolean releaseLock() {
        try(Jedis jedis = jedisPool.getResource()) {
            long del = jedis.del(lockKey);
            return del == 1;
        }
    }
}
