package 流应用;

import java.util.Arrays;
import java.util.List;

/**
 * @CreateTime: 2024年 11月 07日 9:53
 * @Description:
 * @Author: MR.YU
 */
public class lambda_final {
    public static void main(String[] args) {
        boolean a = false;

        try {
             a = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 一个list列表
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        boolean flag = true;
        // 遍历列表，如果a为true，则打印列表中的每个元素，否则不执行任何操作
        list.forEach(i -> {
            System.out.println(flag);
        });
    }
}
