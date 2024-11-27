package 数据交换测试;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreateTime: 2024年 08月 16日 14:41
 * @Description:
 * @Author: MR.YU
 */
public class ListSplitUtil {

    public static <T> List<List<T>> splitList(List<T> list, int size) {
        List<List<T>> lists = new ArrayList<>();
        int numberOfSubLists = (int) Math.ceil((double) list.size() / size);

        for (int i = 0; i < numberOfSubLists; i++) {
            int start = i * size;
            int end = Math.min(start + size, list.size());
            lists.add(list.subList(start, end));
        }

        return lists;
    }
}
