package 代理.动态代理.CGLIB;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @CreateTime: 2025年 04月 27日 10:21
 * @Description:
 * @Author: MR.YU
 */
public class LawyerInterceptor implements MethodInterceptor {

    private Object object;


    public Object getInstance(Object object) {
        this.object = object;
        //Cglib中的加强器，用来创建动态代理
        Enhancer enhancer = new Enhancer();
        //设置代理类的父类
        enhancer.setSuperclass(this.object.getClass());
        //设置回调，这里相当于是对于代理类上所有方法的调用，都会调用Callback，而Callback则需要实现intercept()方法进行拦截
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("律师处理委托人的需求");
        return object;
    }
}
