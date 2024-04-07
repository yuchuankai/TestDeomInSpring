/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 03月 26日 9:49
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 测试ConcurrentHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Date: 2024年 03月 26日 9:49
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("1","1");
        System.out.println(map.get("1"));

        Map<String,String> map1 = new HashMap<>();
        map1.put("1",null);
        map1.put("2",null);
        System.out.println(map1.get("1"));
        System.out.println(map1.get("2"));


        System.out.println("12345678".substring(1));
    }
}




