package 多线程.线程池运行方式;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StopWatch;
import 多线程.线程池.MyThreadFactory;

import java.util.concurrent.*;

/**
 * @CreateTime: 2025年 10月 14日 16:37
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60L, TimeUnit.MICROSECONDS,
                new PriorityBlockingQueue<>(30), new MyThreadFactory(), new CustomRejectedExecutionHandler());
        int num = 50;
        CountDownLatch countDownLatch = new CountDownLatch(num);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 1; i <= num; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });

        }

        try {
            countDownLatch.await();
            stopWatch.stop();
            System.out.println("总耗时：" + stopWatch.getTotalTimeMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}

