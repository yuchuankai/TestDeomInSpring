package 代理.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @CreateTime: 2024年 12月 16日 10:20
 * @Description:
 * @Author: MR.YU
 */
public class LawyerHandler implements InvocationHandler {

    private TrAction action;

    //绑定被代理对象，最后返回代理对象的实例
    public TrAction bind(TrAction action) {
        this.action = action;
        return (TrAction) Proxy.newProxyInstance(
                action.getClass().getClassLoader(),
                action.getClass().getInterfaces(),
                this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用被代理对象的方法
        Object o = method.invoke(action, args);
        //增强方法
        System.out.println("律师处理委托人的需求");
        return o;
    }
}
