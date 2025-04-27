package 多任务框架设计.task;

import 多任务框架设计.engine.Engine;
import 多任务框架设计.engine.SampleEngine;

/**
 * @CreateTime: 2025年 04月 25日 16:50
 * @Description:
 * @Author: MR.YU
 */
public class SampleTask implements Task<String>{

    private String parameter;

    public SampleTask(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getTaskId() {
        return "sampleTask_" + parameter;
    }

    @Override
    public Engine getEngine() {
        return new SampleEngine();
    }

    @Override
    public String execute() {
        // Implement task execution logic here
        return "Result for " + parameter;
    }
}
