package 抽象类.自调用;

/**
 * @CreateTime: 2025年 09月 05日 10:21
 * @Description:
 * @Author: MR.YU
 */
public class B extends  A{
    @Override
    public void createJob() {
        System.out.println("B createJob");
    }
}
