package 多任务框架设计.engine;

import 多任务框架设计.config.PrivateConfig;
import 多任务框架设计.config.PublicConfig;
import 多任务框架设计.task.Task;

/**
 * @CreateTime: 2025年 04月 25日 16:42
 * @Description:
 * @Author: MR.YU
 */
public interface Engine {

    void prepare(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig);
    void execute(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig);
    void commit(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig);
    void rollback(Task<?> task, PublicConfig publicConfig, PrivateConfig privateConfig);

}
