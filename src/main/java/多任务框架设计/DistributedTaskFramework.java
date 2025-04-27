package 多任务框架设计;

import 多任务框架设计.config.PrivateConfig;
import 多任务框架设计.config.PublicConfig;
import 多任务框架设计.statusEnum.ExecutionStatus;
import 多任务框架设计.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2025年 04月 25日 16:48
 * @Description:
 * @Author: MR.YU
 */
public class DistributedTaskFramework {

    private final TaskExecutionContext context = new TaskExecutionContext();
    private final int threadPoolSize;

    public DistributedTaskFramework(PublicConfig publicConfig, int threadPoolSize) {
        this.context.setPublicConfig(publicConfig);
        this.threadPoolSize = threadPoolSize;
    }

    public void addPrivateConfig(String taskId, PrivateConfig config) {
        context.getPrivateConfigs().put(taskId, config);
    }

    public void addTask(Task<?> task) {
        context.getTasks().add(task);
        context.getTaskStats().add(new TaskStats(task.getTaskId()));
    }

    public ExecutionResult execute() throws Exception {
        context.setStatus(ExecutionStatus.EXECUTING);
        List<Task<?>> successfulTasks = new ArrayList<>();

        try {
            // 准备阶段
            for (Task<?> task : context.getTasks()) {
                TaskStats stats = findTaskStats(task.getTaskId());
                stats.setStatus(ExecutionStatus.EXECUTING);
                task.getEngine().prepare(task, context.getPublicConfig(),
                        context.getPrivateConfigs().get(task.getTaskId()));
            }

            // 根据任务数量决定是否使用多线程
            if (context.getTasks().size() > threadPoolSize) {
                ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
                for (Task<?> task : context.getTasks()) {
                    executorService.submit(() -> executeTask(task, successfulTasks));
                }
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.HOURS);
            } else {
                for (Task<?> task : context.getTasks()) {
                    executeTask(task, successfulTasks);
                }
            }

            // 提交阶段
            for (Task<?> task : context.getTasks()) {
                task.getEngine().commit(task, context.getPublicConfig(),
                        context.getPrivateConfigs().get(task.getTaskId()));
            }

            context.setStatus(ExecutionStatus.SUCCESS);
            return ExecutionResult.success();
        } catch (Exception e) {
            context.setStatus(ExecutionStatus.FAILED);
            // 回滚所有已成功执行的任务
            for (Task<?> task : successfulTasks) {
                try {
                    task.getEngine().rollback(task, context.getPublicConfig(),
                            context.getPrivateConfigs().get(task.getTaskId()));
                } catch (Exception rollbackEx) {
                    context.getTaskErrors().put(task.getTaskId(), rollbackEx);
                }
            }
            context.setStatus(ExecutionStatus.ROLLED_BACK);
            return ExecutionResult.failure(e);
        }
    }

    private void executeTask(Task<?> task, List<Task<?>> successfulTasks) {
        TaskStats stats = findTaskStats(task.getTaskId());
        try {
            task.getEngine().execute(task, context.getPublicConfig(),
                    context.getPrivateConfigs().get(task.getTaskId()));
            synchronized (successfulTasks) {
                successfulTasks.add(task);
            }
            stats.setStatus(ExecutionStatus.SUCCESS);
        } catch (Exception e) {
            stats.setStatus(ExecutionStatus.FAILED);
            stats.setErrorMessage(e.getMessage());
            context.getTaskErrors().put(task.getTaskId(), e);
            throw e;
        } finally {
            stats.setEndTime(System.currentTimeMillis());
        }
    }

    private TaskStats findTaskStats(String taskId) {
        return context.getTaskStats().stream()
                .filter(stats -> stats.getTaskId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task stats not found for task: " + taskId));
    }

    public List<TaskStats> getTaskStats() {
        return context.getTaskStats();
    }
}
