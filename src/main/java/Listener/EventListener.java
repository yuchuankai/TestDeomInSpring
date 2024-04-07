/**
 * @Project: testDeomInSpring
 * @ClassName: EventListener
 * @Date: 2024年 02月 27日 13:59
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Listener;

import java.io.File;

/**
 * @Description:
 * @Date: 2024年02月27日 13:59
 * @Author: MR.Yu
 **/
public interface EventListener {

    void update(String eventType, File file);
}
