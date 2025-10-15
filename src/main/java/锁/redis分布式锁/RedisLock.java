package 锁.redis分布式锁;

import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2025年 10月 15日 15:12
 * @Description:
 * @Author: MR.YU
 */
public interface RedisLock {

    boolean acquireLock();

    boolean releaseLock();


}
