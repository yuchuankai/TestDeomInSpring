package ServiceLoaderDemo;


import ServiceLoaderDemo.interfaces.A;

import java.util.ServiceLoader;

/**
 * @CreateTime: 2025年 03月 06日 9:29
 * @Description:
 * @Author: MR.YU
 */
public class main {

    /**
     * 实现类配置@AutoService(A.class)生成 META-INF/services/ServiceLoaderDemo.interfaces.A，才可以使用ServiceLoader
     * 注解@AutoService来自于：
     *          <dependency>
     *             <groupId>com.google.auto.service</groupId>
     *             <artifactId>auto-service</artifactId>
     *             <version>1.0.1</version>
     *         </dependency>
     */

    public static void main(String[] args) {
        ServiceLoader<A> load = ServiceLoader.load(A.class,Thread.currentThread().getContextClassLoader());
        for (A a : load) {
            a.test();
        }
    }
}
