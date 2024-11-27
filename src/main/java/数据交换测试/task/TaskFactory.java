package 数据交换测试.task;

import 数据交换测试.task.datax.TableToTable;

/**
 * @CreateTime: 2024年 10月 09日 9:55
 * @Description:
 * @Author: MR.YU
 */
public class TaskFactory {

    public static Task getTask(TaskConfig taskConfig) {
        if (taskConfig.getType().equals("1")) {
            return new TableToTable();
        }
        throw new RuntimeException("不支持的任务类型");
    }
}
