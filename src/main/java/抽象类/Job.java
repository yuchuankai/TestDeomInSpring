/**
 * @Project: testDeomInSpring
 * @ClassName: Job
 * @Date: 2024年 03月 20日 9:15
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 抽象类;

/**
 * @Description:
 * @Date: 2024年 03月 20日 9:15
 * @Author: MR.Yu
 **/
public abstract class Job  extends AbstractJobPlugin{

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    private String jobName;
}
