package 抽象类.自调用;

/**
 * @CreateTime: 2025年 09月 05日 10:20
 * @Description:
 * @Author: MR.YU
 */
public class mian {

    public static void main(String[] args) {
        A a = new B();
        a.createJob();
    }
}
