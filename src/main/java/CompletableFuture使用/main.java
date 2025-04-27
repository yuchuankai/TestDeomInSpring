package CompletableFuture使用;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @CreateTime: 2025年 04月 11日 14:54
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) throws InterruptedException {
        //当前调用者线程为:main
        System.out.println("当前调用者线程为:" + Thread.currentThread().getName());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1; i++) {
            System.out.println(i);
            CompletableFuture.runAsync(() -> {
                // 异步方法内当前执行线程为:ForkJoinPool.commonPool-worker-1
                System.out.println("异步方法内当前执行线程为:" + Thread.currentThread().getName());
                System.out.println(111);
                new FlushTask("线程1");
            });
        }
        System.out.println("end");
        countDownLatch.await();
    }
}
