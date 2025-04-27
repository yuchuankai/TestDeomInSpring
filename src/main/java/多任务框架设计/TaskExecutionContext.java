package 多任务框架设计;

import lombok.Getter;
import lombok.Setter;
import 多任务框架设计.config.PrivateConfig;
import 多任务框架设计.config.PublicConfig;
import 多任务框架设计.statusEnum.ExecutionStatus;
import 多任务框架设计.task.Task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
*@CreateTime: 2025年 04月 25日 16:47
*@Description: 
*@Author: MR.YU
*/
@Getter
public class TaskExecutionContext {

    @Setter
    private PublicConfig publicConfig;
    private final Map<String, PrivateConfig> privateConfigs = new ConcurrentHashMap<>();
    private final List<Task<?>> tasks = new CopyOnWriteArrayList<>();
    @Setter
    private ExecutionStatus status = ExecutionStatus.PENDING;
    private final Map<String, Exception> taskErrors = new ConcurrentHashMap<>();
    private final List<TaskStats> taskStats = new CopyOnWriteArrayList<>();

}
