/**
 * @Project: testDeomInSpring
 * @ClassName: LogOpenListener
 * @Date: 2024年 02月 27日 14:03
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Listener;

import java.io.File;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:03
 * @Author: MR.Yu
 **/
public class LogOpenListener implements EventListener{

    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Save to log " + log + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}
