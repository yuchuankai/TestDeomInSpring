package 代理.动态代理;

/**
 * @CreateTime: 2024年 12月 16日 10:19
 * @Description:
 * @Author: MR.YU
 */
public class Client implements TrAction {
    @Override
    public void litigation() {
        System.out.println("委托人：我有诉讼的需求");
    }

    @Override
    public void consult() {
        System.out.println("委托人：我有咨询的需求");
    }
}
