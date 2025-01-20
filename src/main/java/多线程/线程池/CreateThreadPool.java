package 多线程.线程池;

import lombok.Getter;

import java.util.concurrent.*;

/**
 * @CreateTime: 2024年 12月 23日 9:34
 * @Description:
 * @Author: MR.YU
 */
@Getter
public class CreateThreadPool {


    private ThreadPoolExecutor threadPoolExecutor;

    public CreateThreadPool () {
        createPool();
    }

    private void createPool() {
        threadPoolExecutor =  new ThreadPoolExecutor(5, 10, 60L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(1), new MyThreadFactory());
    }


    /**
     * 预启动线程,事先启动一个核心线程
     */
    public boolean preStartCoreThread(){
        return threadPoolExecutor.prestartCoreThread();
    }

    /**
     *预启动线程,事先启动所有核心线程
     */
    public int preStartAllCoreThreads(){
        return threadPoolExecutor.prestartAllCoreThreads();
    }

    /**
     * 获取队列中的任务数
     */
    public int getQueueSize(){
        return threadPoolExecutor.getQueue().size();
    }

    /**
     * 获取当前活跃的线程数
     */
    public int getActiveCount(){
        return threadPoolExecutor.getActiveCount();
    }

    /**
     * 修改核心线程数
     */
    public void setCorePoolSize(int corePoolSize){
        threadPoolExecutor.setCorePoolSize(corePoolSize);
    }
}
