/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 02月 27日 14:04
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Listener;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:04
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.events.subscribe("open", new LogOpenListener("C:\\Users\\yuchuankai\\Desktop\\文档\\2024.1\\mysql-kingbase.json"));
        editor.events.subscribe("save", new EmailNotificationListener("aa@qq.com"));

        try{
            editor.openFile("C:\\Users\\yuchuankai\\Desktop\\文档\\2024.1\\mysql-kingbase.json");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
