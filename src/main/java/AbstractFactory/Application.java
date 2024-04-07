/**
 * @Project: testDeomInSpring
 * @ClassName: Application
 * @Date: 2024年 02月 29日 14:24
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package AbstractFactory;

import AbstractFactory.Button.Button;
import AbstractFactory.Checkbox.Checkbox;
import AbstractFactory.Factory.GUIFactory;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:24
 * @Author: MR.Yu
 **/
public class Application {

    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}
