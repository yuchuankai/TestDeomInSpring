package 多线程.定时线程;

import java.util.concurrent.*;

/**
 * @CreateTime: 2025年 09月 19日 13:52
 * @Description:
 * @Author: MR.YU
 */
public class ScheduledThread {

    /**
     *  线程池创建的线程是非守护线程，当主线程出现异常后，线程池的线程会继续执行并阻止java程序退出。可以在创建线程池的时候设置线程池的线程为守护线程这样会随着主线程的退出跟随退出。
     */

    protected static ScheduledExecutorService scheduler;

    protected static ThreadPoolExecutor executor;

    protected static ScheduledFuture scheduledFuture;

    public static void main(String[] args) {

        ScheduledThread scheduledThread = new ScheduledThread();
        boolean b = scheduledThread.executeTask();
        if (b) {
            System.exit(0);
        } else {
            System.exit(-1);
        }

    }

    private boolean executeTask() {

        initTimingSubmitTask1();

        String port = "1002/fss";
        Integer i = Integer.valueOf(port);
        return i > 0;
    }


    private void initTimingSubmitTask() {
        scheduler = new ScheduledThreadPoolExecutor(1);
        int interval = 15;
        scheduledFuture =
                scheduler.scheduleWithFixedDelay(
                        () -> {
                            System.out.println("定时任务执行中...");
                        },
                        interval, interval, TimeUnit.SECONDS);
    }

    private void initTimingSubmitTask1() {
        executor =  new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 15);
                    System.out.println("定时任务执行中...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
