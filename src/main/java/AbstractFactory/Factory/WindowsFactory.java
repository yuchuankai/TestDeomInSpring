/**
 * @Project: testDeomInSpring
 * @ClassName: WindowsFactory
 * @Date: 2024年 02月 29日 14:23
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory.Factory;

import AbstractFactory.Button.Button;
import AbstractFactory.Button.MacOSButton;
import AbstractFactory.Button.WindowsButton;
import AbstractFactory.Checkbox.Checkbox;
import AbstractFactory.Checkbox.MacOSCheckbox;
import AbstractFactory.Checkbox.WindowsCheckbox;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:23
 * @Author: MR.Yu
 **/
public class WindowsFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
