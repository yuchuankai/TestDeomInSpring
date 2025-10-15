package 多线程.线程池;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @CreateTime: 2024年 12月 23日 9:43
 * @Description:
 * @Author: MR.YU
 */
public class MyThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public MyThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "MyThreadFactoryCreatePool-" +
                poolNumber.getAndIncrement() +
                "-MyThread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        System.out.println("线程池创建线程：-----------------------------");
        String name = namePrefix + threadNumber.getAndIncrement();
        System.out.println("线程池创建线程名称：" + name);
        Thread t = new Thread(group, r,
                name,
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
