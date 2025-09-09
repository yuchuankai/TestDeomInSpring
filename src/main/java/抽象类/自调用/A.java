package 抽象类.自调用;

/**
 * @CreateTime: 2025年 09月 05日 10:20
 * @Description:
 * @Author: MR.YU
 */
public abstract class A {

    public void createReader(){
        this.createJob();
    }
    public abstract void createJob();
}
