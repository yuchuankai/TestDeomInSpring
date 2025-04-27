package 多任务框架设计;

import lombok.Data;
import 多任务框架设计.statusEnum.ExecutionStatus;

/**
 * @CreateTime: 2025年 04月 25日 16:44
 * @Description:
 * @Author: MR.YU
 */
@Data
public class TaskStats {
    private String taskId;
    private ExecutionStatus status;
    private long startTime;
    private long endTime;
    private String errorMessage;

    public TaskStats(String taskId) {
        this.taskId = taskId;
        this.startTime = System.currentTimeMillis();
    }

}
