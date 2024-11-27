package 数据交换测试.task;

/**
 * @CreateTime: 2024年 10月 09日 9:58
 * @Description:
 * @Author: MR.YU
 */
public class TaskConfig {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    private String type;

    private String taskType;

    public TaskConfig(String type, String taskType) {
        this.type = type;
        this.taskType = taskType;
    }
}
