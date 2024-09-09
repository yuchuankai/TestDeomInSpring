package list删除;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2024年 07月 24日 10:46
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) {
        ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        map.put("1", list);
        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("4");
        map.put("2", list2);
        List<String> list3 = new ArrayList<>();
        list3.add("3");
        list3.add("4");
        map.put("3", list3);
        List<String> list4 = new ArrayList<>();
        list4.add("3");
        list4.add("4");
        map.put("4", list4);
        map.forEach((k, v) -> {
            for (int i = 0; i < v.size(); i++) {
                String s = v.get(i);
                if (s.equals("3")){
                    v.set(i,"nihao");
                }
            }
            List<String> collect = v.stream().filter(s -> !s.equals("1")).collect(Collectors.toList());
            map.remove(k);
        });
        System.out.println(map);
    }
}
