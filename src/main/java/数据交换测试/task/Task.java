package 数据交换测试.task;

import org.slf4j.Logger;
import 数据交换测试.ListSplitUtil;
import 数据交换测试.task.datax.Datax;
import 数据交换测试.task.datax.FileToTable;

import java.io.Serializable;
import java.util.List;

/**
 * @CreateTime: 2024年 10月 08日 14:02
 * @Description:
 * @Author: MR.YU
 */
public abstract class Task implements Serializable {

    protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(Task.class);

    private String type;

    public void init() {
        this.type = "datax";
//        return CreateConstructor(1);
    }

    private static Task CreateConstructor(int a){
        if (a == 1) {
            return Datax.CreateConstructor(a);
        }
        if (a == 2) {
            return new FileToTable();
        }
        throw new RuntimeException("不支持该类型");
    }


    public abstract void split();

    public abstract String createSubInstance();

    // 任务拆分器
    public static <T> List<List<T>> splitList(List<T> list, int size)
    {
        return ListSplitUtil.splitList(list, size);
    }
}
