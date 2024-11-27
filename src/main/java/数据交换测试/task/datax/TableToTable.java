package 数据交换测试.task.datax;


import 数据交换测试.task.Task;

/**
 * @CreateTime: 2024年 10月 08日 14:04
 * @Description:
 * @Author: MR.YU
 */
public class TableToTable extends Datax {


    public TableToTable() {
    }



    @Override
    public void split() {
        System.out.println("TableToTable split !");
    }

    public String createSubInstance(){

        System.out.println("同源同目的拆分");

        System.out.println("计算增量同步的范围");

        return createDataXJob();
    }


    @Override
    public String createDataXJob() {
        return "TableToTable Json !";
    }
}
