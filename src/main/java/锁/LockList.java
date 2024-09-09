/**
 * @Project: testDeomInSpring
 * @ClassName: LockList
 * @Date: 2024年 06月 06日 15:31
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 锁;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Date: 2024年 06月 06日 15:31
 * @Author: MR.Yu
 **/
public class LockList {

    private static final List<String> LIST = new LinkedList<>();
    public static void main(String[] args) {
        LIST.add("1");
        LIST.add("2");

        Thread t1 = new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                addList("1");
                addList("2");
            }
        });

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                removeList("1");
                removeList("2");
            }
        });

        t1.start();
        t2.start();
        LIST.forEach(System.out::println);
    }


    private static void addList(String id){
        synchronized (LIST) {
            LIST.add(id);
        }
    }

    private static void removeList(String id){
        synchronized (LIST) {
            LIST.remove(id);
        }
    }

}
