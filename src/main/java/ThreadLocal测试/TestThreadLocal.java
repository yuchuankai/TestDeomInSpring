package ThreadLocal测试;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;


/**
 * @CreateTime: 2024年 12月 16日 16:46
 * @Description:
 * @Author: MR.YU
 */
public class TestThreadLocal {

    private static ThreadLocal<Integer> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        // 当前线程和子线程共享数据
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set(1);
        tl.set(1);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + " : " + tl.get());
        }).start();
        
        System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());
        System.out.println(Thread.currentThread().getName() + " : " + tl.get());

        tl.remove();
    }

}
