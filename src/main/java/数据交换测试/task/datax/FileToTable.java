package 数据交换测试.task.datax;

import 数据交换测试.task.Task;

/**
 * @CreateTime: 2024年 10月 08日 14:05
 * @Description:
 * @Author: MR.YU
 */
public class FileToTable extends Datax {


    public FileToTable() {

    }

    @Override
    public String createDataXJob() {
        return "";
    }

    @Override
    public void split() {

    }

    @Override
    public String createSubInstance() {
        return createDataXJob();
    }
}
