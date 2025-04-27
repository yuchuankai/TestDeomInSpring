package 多任务框架设计.engine;

import 多任务框架设计.config.PrivateConfig;
import 多任务框架设计.config.PublicConfig;
import 多任务框架设计.task.Task;

/**
 * @CreateTime: 2025年 04月 25日 16:51
 * @Description:
 * @Author: MR.YU
 */
public class SampleEngine implements Engine {

    @Override
    public void prepare(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig) {
        System.out.println("Preparing task: " + task.getTaskId());
    }

    @Override
    public void execute(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig) {
        System.out.println("Executing task: " + task.getTaskId());
        task.execute();
    }

    @Override
    public void commit(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig) {
        System.out.println("Committing task: " + task.getTaskId());
    }

    @Override
    public void rollback(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig) {
        System.out.println("Rolling back task: " + task.getTaskId());
    }
}
