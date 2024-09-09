package 并发;

/**
 * @CreateTime: 2024年 09月 03日 17:00
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) {
        new MyThread().start();
    }


    static class MyThread extends Thread{

        @Override
        public synchronized void start() {
            super.setName("MyThread");
            super.start();
        }
        @Override
        public void run() {
            System.out.println("线程执行了");
        }
    }
}
