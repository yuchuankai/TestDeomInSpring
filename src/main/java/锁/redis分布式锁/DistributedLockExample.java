package 锁.redis分布式锁;

/**
 * @CreateTime: 2025年 10月 15日 13:45
 * @Description:
 * @Author: MR.YU
 */
public class DistributedLockExample {

    public static void main(String[] args) {
        String redisHost = "10.0.47.74";

        int redisPort = 6379;

        String lockKey = "order:create:lock";

        RedisLock redisLock = testRedissonDistributedLock("redis://" + redisHost + ":" + redisPort, lockKey);

        testRedisLock(redisLock);

    }

    public static RedisLock testImprovedRedisLock(String redisHost, int redisPort, String lockKey){
        return new ImprovedRedisLock(redisHost, redisPort, lockKey,30);
    }



    public static RedisLock testSimpleRedisLock(String redisHost, int redisPort, String lockKey){
        return new SimpleRedisLock(redisHost, redisPort, lockKey);
    }

    public static RedisLock testRedissonDistributedLock(String url, String lockKey){
        return new RedissonDistributedLock(url, lockKey);
    }

    public static void testRedisLock(RedisLock redisLock){
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (redisLock.acquireLock()) {
                    System.out.println("线程1获取锁成功");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisLock.releaseLock();
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程1获取锁失败");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                if (redisLock.acquireLock()) {
                    System.out.println("线程2获取锁成功");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisLock.releaseLock();
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程2获取锁失败");
                }
            }
        });

        thread1.start();
        thread2.start();

    }
}
