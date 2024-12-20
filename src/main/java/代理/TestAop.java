package 代理;

import 代理.动态代理.LawyerHandler;
import 代理.动态代理.TrAction;
import 代理.静态代理.Action;
import 代理.静态代理.Client;
import 代理.静态代理.Lawyer;

/**
 * @CreateTime: 2024年 12月 16日 10:06
 * @Description:
 * @Author: MR.YU
 */
public class TestAop {

    public static void main(String[] args) {
        test2();
    }

    /**
     * 静态代理：在静态代理中，静态代理对象=实现一个与被代理对象相同的接口+增强方法。
     * Spring中的AOP思想就是对代理模式的经典运用，下面先讲讲代理模式的核心思想，以静态代理为例.
     * 下面有这样一个例子，委托人在遭遇利益受损的时候，可以委托律师帮忙打官司。
     */
    public static void test1(){
        Action client = new Client();
        Action lawyer = new Lawyer(client);
        lawyer.handle();
    }

    /**
     * 动态代理：在动态代理中，代理对象=实现一个与被代理对象相同的接口+增强方法。
     * 动态代理的原理是：在运行时，JVM根据被代理对象生成一个代理对象，
     * 代理对象和被代理对象实现相同的接口，代理对象通过反射调用被代理对象的方法，
     * 并在方法调用前后执行一些额外的代码，从而实现代理模式的功能。
     * JDK动态代理，需要被代理类实现某个接口，如果该类没有实现接口，则无法使用JDK动态代理
     */
    public static void test2(){
        TrAction client = new 代理.动态代理.Client();
        TrAction lawyer = new LawyerHandler().bind(client);
        lawyer.litigation();
        lawyer.consult();
    }
}
