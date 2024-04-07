/**
 * @Project: testDeomInSpring
 * @ClassName: Editor
 * @Date: 2024年 02月 27日 14:02
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Listener;

import java.io.File;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:02
 * @Author: MR.Yu
 **/
public class Editor {

    public EventManager events;
    private File file;

    public Editor() {
        this.events = new EventManager("open", "save");
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.notify("open", file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }
}
