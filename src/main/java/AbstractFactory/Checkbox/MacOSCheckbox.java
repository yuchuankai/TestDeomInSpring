/**
 * @Project: testDeomInSpring
 * @ClassName: MacOSCheckbox
 * @Date: 2024年 02月 29日 14:20
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory.Checkbox;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:20
 * @Author: MR.Yu
 **/
public class MacOSCheckbox implements Checkbox{
    @Override
    public void paint() {
        System.out.println("You have created MacOSCheckbox.");
    }
}
