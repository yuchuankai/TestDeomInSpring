/**
 * @Project: testDeomInSpring
 * @ClassName: ListForEach
 * @Date: 2024年 05月 15日 14:26
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 流应用;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Date: 2024年 05月 15日 14:26
 * @Author: MR.Yu
 **/
public class ListForEach {

    public static void main(String[] args) {
        String a = "aaa";
        String bbb = a.concat("bbb");
    }





    public static void foreachSleep(){
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");

        AtomicInteger index = new AtomicInteger(1);
        list.forEach(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(s);
        });
    }
}
