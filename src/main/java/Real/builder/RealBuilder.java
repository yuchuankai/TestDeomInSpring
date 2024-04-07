/**
 * @Project: testDeomInSpring
 * @ClassName: RealBuilder
 * @Date: 2024年 03月 12日 10:46
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.builder;

import Real.Reader;
import Real.Writer;
import Real.task.RealTask;
import Real.task.Task;

/**
 * @Description:
 * @Date: 2024年 03月 12日 10:46
 * @Author: MR.Yu
 **/
public class RealBuilder implements Builder{

    private final Task task = new RealTask();

    @Override
    public void buildJob() {
        task.createJob();
    }

    @Override
    public void buildReader(Reader reader) {
        task.createReader(reader);
    }

    @Override
    public void buildWriter(Writer writer) {
        task.createWriter(writer);
    }


    @Override
    public void buildSetting() {
        task.createSetting();
    }

    @Override
    public String getTask() {
        return task.getStringTask();
    }
}
