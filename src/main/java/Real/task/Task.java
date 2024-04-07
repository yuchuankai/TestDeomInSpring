/**
 * @Project: testDeomInSpring
 * @ClassName: Task
 * @Date: 2024年 03月 12日 10:36
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.task;

import Real.Reader;
import Real.Writer;

/**
 * @Description:
 * @Date: 2024年 03月 12日 10:36
 * @Author: MR.Yu
 **/
public abstract class Task {

    public abstract void createJob();

    public abstract void createReader(Reader reader);

    public abstract void createWriter(Writer writer);

    public abstract void createSetting();

    public abstract String getStringTask();
}
