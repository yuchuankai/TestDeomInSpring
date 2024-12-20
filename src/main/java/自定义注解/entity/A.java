package 自定义注解.entity;


import 自定义注解.Load;

/**
 * @CreateTime: 2024年 12月 13日 13:43
 * @Description:
 * @Author: MR.YU
 */
public class A {
    @Load
    private B b;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public B getB() {
        return b;
    }
}
