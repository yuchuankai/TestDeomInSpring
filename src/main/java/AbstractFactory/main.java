/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 02月 29日 14:25
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory;

import AbstractFactory.Factory.GUIFactory;
import AbstractFactory.Factory.MacOSFactory;
import AbstractFactory.Factory.WindowsFactory;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:25
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        Application app;
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("当前操作系统：" + osName);
        if (osName.contains("mac")) {
            factory = new MacOSFactory();
        } else {
            factory = new WindowsFactory();
        }
        app = new Application(factory);
        app.paint();
    }
}
