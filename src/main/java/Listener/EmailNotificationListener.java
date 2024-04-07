/**
 * @Project: testDeomInSpring
 * @ClassName: EmailNotificationListener
 * @Date: 2024年 02月 27日 14:00
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Listener;

import java.io.File;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:00
 * @Author: MR.Yu
 **/
public class EmailNotificationListener implements EventListener{

    private String email;

    public EmailNotificationListener(String email) {
        this.email = email;
    }
    @Override
    public void update(String eventType, File file) {
        System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}
