package 数据交换测试.task.datax;

import 数据交换测试.task.Task;

/**
 * @CreateTime: 2024年 10月 08日 14:03
 * @Description:
 * @Author: MR.YU
 */
public abstract class Datax extends Task {

    public static Task CreateConstructor(int a){
        if (a == 1) {
            return new TableToTable();
        }
        if (a == 2) {
            return new FileToTable();
        }
        throw new RuntimeException("不支持该类型");
    }

    public abstract String createDataXJob();
}
