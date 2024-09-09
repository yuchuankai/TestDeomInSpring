/**
 * @Project: testDeomInSpring
 * @ClassName: StreamToNewMap
 * @Date: 2024年 06月 05日 9:12
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 流应用;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Date: 2024年 06月 05日 9:12
 * @Author: MR.Yu
 **/
public class StreamToNewMap {

    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", null);
        map.put("2", "3");
        map.put("3", "4");
        Map<Object, Object> collect = map.entrySet().stream().collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);

        map.put("4", "5");
        map.put("1", "0");
//        map.clear();

        System.out.println(map);
        System.out.println(collect);


    }
}
