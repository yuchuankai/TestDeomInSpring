/**
 * @Project: testDeomInSpring
 * @ClassName: WindowsButton
 * @Date: 2024年 02月 29日 14:19
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory.Button;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:19
 * @Author: MR.Yu
 **/
public class WindowsButton implements Button{
    @Override
    public void paint() {
        System.out.println("You have created WindowsButton.");
    }
}
