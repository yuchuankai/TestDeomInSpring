/**
 * @Project: testDeomInSpring
 * @ClassName: HtmlButton
 * @Date: 2024年 02月 29日 14:10
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package FactoryMethod;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:10
 * @Author: MR.Yu
 **/
public class HtmlButton implements Button{
    @Override
    public void render() {
        System.out.println("<button>Test Button</button>");
        onClick();
    }

    @Override
    public void onClick() {
        System.out.println("Click! Button says - 'Hello World!'");
    }
}
