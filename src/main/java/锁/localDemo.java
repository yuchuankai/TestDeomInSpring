/**
 * @Project: testDeomInSpring
 * @ClassName: localDemo
 * @Date: 2024年 04月 18日 15:59
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 锁;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Description:
 * @Date: 2024年 04月 18日 15:59
 * @Author: MR.Yu
 **/
public class localDemo {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };


    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Account a = new Account(lock);
        Account b = new Account(lock);
        System.out.println(a.balance);
        System.out.println(b.balance);
        Thread object1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                a.transfer(b, 3);
            }
        });

        Thread object2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                b.transfer(a, 2);
            }
        });
        object1.start();
        object2.start();
//        Thread.sleep(1000);
        System.out.println(a.balance);
        System.out.println(b.balance);
    }



    static class Account{
        private int balance;
        private final Object lock;
        Account(Object lock) {
            this.lock = lock;
            this.balance = 100000;
        }
        void transfer(Account target, int amt) {
            synchronized(this.lock) {
                Integer integer = threadLocal.get();

//                System.out.println(Thread.currentThread().getName() + ":" + integer + " amt : " + amt);
//                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
                threadLocal.set(++integer);
//                threadLocal.remove();
            }
        }
    }
}
