/**
 * @Project: testDeomInSpring
 * @ClassName: MacOSFactory
 * @Date: 2024年 02月 29日 14:22
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory.Factory;

import AbstractFactory.Button.Button;
import AbstractFactory.Button.MacOSButton;
import AbstractFactory.Checkbox.Checkbox;
import AbstractFactory.Checkbox.MacOSCheckbox;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:22
 * @Author: MR.Yu
 **/
public class MacOSFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}
