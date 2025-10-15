package 多线程.线程池运行方式;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @CreateTime: 2025年 10月 14日 16:48
 * @Description:
 * @Author: MR.YU
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("线程池拒绝任务：" + r.toString());
    }
}
