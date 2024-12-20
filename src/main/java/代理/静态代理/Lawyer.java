package 代理.静态代理;

/**
 * @CreateTime: 2024年 12月 16日 10:08
 * @Description: 律师
 * @Author: MR.YU
 */
public class Lawyer implements Action {

    private Action action;

    public Lawyer(Action action) {
        this.action = action;
    }

    @Override
    public void handle() {
        action.handle();
        System.out.println("律师：帮助我的委托人打官司");
    }
}
