/**
 * @Project: testDeomInSpring
 * @ClassName: OfflineJob
 * @Date: 2024年 02月 26日 15:34
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:34
 * @Author: MR.Yu
 **/
public class OfflineTask extends Task {

    private static final long serialVersionUID = 3875012010277005819L;


    private Job job;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
