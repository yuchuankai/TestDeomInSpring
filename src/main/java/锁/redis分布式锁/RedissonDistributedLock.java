package 锁.redis分布式锁;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;


/**
 * @CreateTime: 2025年 10月 15日 15:23
 * @Description:
 * @Author: MR.YU
 */
public class RedissonDistributedLock implements RedisLock{

    private final RedissonClient redissonClient;

    private final String lockKey;

    private final TimeUnit unit = TimeUnit.MILLISECONDS;


    public RedissonDistributedLock(String redisUrl, String lockKey) {
        Config config = new Config();
        config.useSingleServer().setAddress(redisUrl);
        config.useSingleServer().setPassword("1qaz!QAZ123,,");
        this.redissonClient = Redisson.create(config);
        this.lockKey = lockKey;
    }

    @Override
    public boolean acquireLock() {
        RLock lock = redissonClient.getLock(lockKey);
        // 锁持有时间
        long leaseTime = 30000;
        // 获取锁等待时间
        long waitTime = 1000;
//        lock.lock(leaseTime, unit);

        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean releaseLock() {
        try{
            RLock lock = redissonClient.getLock(lockKey);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            redissonClient.shutdown();
        }
    }
}
