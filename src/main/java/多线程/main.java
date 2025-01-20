package 多线程;

import 多线程.线程池.CreateThreadPool;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2024年 09月 03日 17:00
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 线程池测试：
     *  execute()没有返回值，submit（）方法可以通过get方法获取异步的执行结果
     *  execute方法会打印异常但是无法捕获异常；submit可以通过get方法获取异步的执行结果并捕获异常，如果没有调用get方法则获取不到异常也不会打印异常。
     *  线程池的关闭：
     *  调用shutdown()方法会会等待线程池的线程执行完成后关闭任务。
     *  调用shutdownNow()方法会立即关闭线程池，并且返回未执行的任务。
     */
    public static void test1() {
        CreateThreadPool createThreadPool = new CreateThreadPool();
        ThreadPoolExecutor threadPoolExecutor = createThreadPool.getThreadPoolExecutor();
        for (int i = 1; i <= 8; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                System.out.println("线程执行了");
                try {
                    Thread.sleep(1000* finalI *2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        threadPoolExecutor.shutdown();
        System.out.println("线程执行完毕");

        try {
            System.out.println("查看线程是否执行完成，等待10秒后强制关闭");
            if (!threadPoolExecutor.awaitTermination(15, TimeUnit.SECONDS)) {
                System.out.println("强制关闭线程池");
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    static class MyThread extends Thread{

        @Override
        public synchronized void start() {
            super.setName("MyThread");
            super.start();
        }
        @Override
        public void run() {
            System.out.println("线程执行了");
        }
    }
}
