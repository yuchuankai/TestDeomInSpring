package 多线程;

/**
 * @CreateTime: 2025年 08月 21日 15:10
 * @Description:
 * @Author: MR.YU
 */
public class DeBugTest {

    public static void main(String[] args) {

        System.out.println("aaa");

        new Thread(()->{
            System.out.println("1");
        }).start();

        System.out.println("bbb");
    }



}
