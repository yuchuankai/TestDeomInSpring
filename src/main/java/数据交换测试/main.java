package 数据交换测试;

import 数据交换测试.task.Task;
import 数据交换测试.task.TaskConfig;
import 数据交换测试.task.TaskFactory;

/**
 * @CreateTime: 2024年 10月 08日 14:12
 * @Description:
 * @Author: MR.YU
 */
public class main {
    public static void main(String[] args) {
        TaskConfig taskConfig = new TaskConfig("1", "2");
        Task task = TaskFactory.getTask(taskConfig);

//        Task task = Task.init();
//
//        task.split();
//
//        String jobJson = task.createSubInstance();
//
//        System.out.println(jobJson);



        // 1.初始化

        // 2.选择构造器

        // 3.检查

        // 4.拆分

        // 5.生成任务
    }
}
