/**
 * @Project: testDeomInSpring
 * @ClassName: HtmlDialog
 * @Date: 2024年 02月 29日 14:12
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package FactoryMethod;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:12
 * @Author: MR.Yu
 **/
public class HtmlDialog extends Dialog{
    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}
