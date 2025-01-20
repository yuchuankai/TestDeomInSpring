package 锁;

/**
 * @CreateTime: 2025年 01月 02日 15:06
 * @Description:
 * @Author: MR.YU
 */
public class WaitAndNotify {

    public static void main(String[] args) {
        WaitAndNotifyDemo waitAndNotifyDemo = new WaitAndNotifyDemo();
        waitAndNotifyDemo.waitAndNotify1();
    }
}

class WaitAndNotifyDemo {
    private final Object lock = new Object();
    private volatile boolean flag = false;

    public void waitAndNotify() {


        Thread object1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    if (flag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("object1:" + i);
                    flag = !flag;
                    lock.notify();
                }
            }
        });
        Thread object2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    if (!flag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("object2:" + i);
                    flag = !flag;
                    lock.notify();
                }
            }
        });

        object1.start();
        object2.start();
    }


    public void waitAndNotify1() {


        Thread object1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while(flag) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("object1:" + i);
                flag = !flag;
            }
        });
        Thread object2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while(!flag) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("object2:" + i);
                flag = !flag;
            }
        });

        object1.start();
        object2.start();
    }
}