/**
 * @Project: testDeomInSpring
 * @ClassName: Builder
 * @Date: 2024年 03月 12日 10:33
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.builder;

import Real.Reader;
import Real.Writer;
import Real.task.Task;

/**
 * @Description:
 * @Date: 2024年03月12日 10:33
 * @Author: MR.Yu
 **/
public interface Builder {

    public void buildJob();

    public void buildReader(Reader reader);

    public void buildWriter(Writer writer);

    public void buildSetting();

    public String getTask();
}
