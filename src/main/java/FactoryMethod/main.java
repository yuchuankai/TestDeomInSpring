/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 02月 29日 14:13
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package FactoryMethod;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:13
 * @Author: MR.Yu
 **/
public class main {

    private static final String WINDOWS = "Windows 11";

    public static void main(String[] args) {
        Dialog dialog = null;
        System.out.println("当前操作系统：" + System.getProperty("os.name"));
        if (WINDOWS.equals(System.getProperty("os.name"))) {
            dialog = new WindowsDialog();
        } else {
            dialog = new HtmlDialog();
        }
        dialog.renderWindow();
    }
}
