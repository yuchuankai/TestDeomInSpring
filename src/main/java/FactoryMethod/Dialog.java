/**
 * @Project: testDeomInSpring
 * @ClassName: Dialog
 * @Date: 2024年 02月 29日 14:11
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package FactoryMethod;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:11
 * @Author: MR.Yu
 **/
public abstract class Dialog {

    public void renderWindow() {
        // ... other code ...

        Button okButton = createButton();
        okButton.render();
    }

    public abstract Button createButton();
}
