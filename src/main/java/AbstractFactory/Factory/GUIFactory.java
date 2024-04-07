/**
 * @Project: testDeomInSpring
 * @ClassName: GUIFactory
 * @Date: 2024年 02月 29日 14:21
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory.Factory;

import AbstractFactory.Button.Button;
import AbstractFactory.Checkbox.Checkbox;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:21
 * @Author: MR.Yu
 **/
public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
