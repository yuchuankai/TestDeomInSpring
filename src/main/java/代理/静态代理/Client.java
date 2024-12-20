package 代理.静态代理;

/**
 * @CreateTime: 2024年 12月 16日 10:08
 * @Description: 委托人
 * @Author: MR.YU
 */
public class Client implements Action {
    @Override
    public void handle() {
        System.out.println("委托人：我要找律师,帮我打官司");
    }
}
