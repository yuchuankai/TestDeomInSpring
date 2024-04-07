/**
 * @Project: testDeomInSpring
 * @ClassName: RealTask
 * @Date: 2024年 03月 12日 10:35
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.task;

import Real.Reader;
import Real.Writer;
import task.Job;
import task.utils.JsonUtils;

/**
 * @Description:
 * @Date: 2024年 03月 12日 10:35
 * @Author: MR.Yu
 **/
public class RealTask extends Task{

    private Job job;

    @Override
    public void createJob(){
        job = new Job();
    }

    @Override
    public void createReader(Reader reader) {
    }

    @Override
    public void createWriter(Writer writer) {

    }

    @Override
    public void createSetting() {

    }

    @Override
    public String getStringTask() {
        return JsonUtils.stringFromJob(this.job);
    }

}
