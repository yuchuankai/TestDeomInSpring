package Map类测试;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @CreateTime: 2024年 12月 27日 10:08
 * @Description:
 * @Author: MR.YU
 */
public class TestConcurrentHashMap {

    public static void main(String[] args) {

    }

    public static void test1() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        System.out.println(map);
    }
}
