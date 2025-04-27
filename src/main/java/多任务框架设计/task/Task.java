package 多任务框架设计.task;

import 多任务框架设计.engine.Engine;

/**
 * @CreateTime: 2025年 04月 25日 16:42
 * @Description:
 * @Author: MR.YU
 */
public interface Task<T> {

    String getTaskId();
    Engine getEngine();
    T execute();
}
