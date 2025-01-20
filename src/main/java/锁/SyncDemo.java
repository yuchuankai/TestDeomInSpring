package 锁;

/**
 * @CreateTime: 2025年 01月 02日 9:43
 * @Description:
 * @Author: MR.YU
 */
public class SyncDemo {


    public static void main(String[] args) {
        Account account = new Account();

        new Thread(() -> {
            account.set(100);
        }, "A").start();
        new Thread(() -> {
            account.get();
        }, "B").start();
    }
}


class Account {

    private int balance;

    public synchronized void set(int balance) {
        System.out.println(Thread.currentThread().getName() + " : " + balance);

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " : " + balance);
        this.balance = balance;
    }

    public synchronized int get() {
        System.out.println(Thread.currentThread().getName() + " : " + balance);
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " : " + balance);
        return balance;
    }

    public void add(int balance) {
        synchronized(this) {
            System.out.println(Thread.currentThread().getName() + " : " + balance);
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.balance += balance;
        }
    }
}
