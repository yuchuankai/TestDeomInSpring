package 多任务框架设计;

import 多任务框架设计.config.PrivateConfig;
import 多任务框架设计.config.PublicConfig;
import 多任务框架设计.task.SampleTask;

/**
 * @CreateTime: 2025年 04月 25日 16:49
 * @Description:
 * @Author: MR.YU
 */
public class FrameworkUsageExample {

    public static void main(String[] args) {
//       new Thread(FrameworkUsageExample::example).start();

        SampleTask param1 = new SampleTask("param1");
        SampleTask param2 = new SampleTask("param1");
        System.out.println(param1.hashCode());
        System.out.println(param2.hashCode());
    }


    private static void example() {
        // 1. 初始化公共配置
        PublicConfig publicConfig = new PublicConfig("https://api.example.com", 1000);

        // 2. 创建框架实例，指定线程池大小的阈值
        int threadPoolSize = 4; // 阈值，任务数量超过这个值时启用多线程
        DistributedTaskFramework framework = new DistributedTaskFramework(publicConfig, threadPoolSize);

        // 3. 添加私有配置
        framework.addPrivateConfig("task1", new PrivateConfig("/endpoint1"));
        framework.addPrivateConfig("task2", new PrivateConfig("/endpoint2"));

        // 4. 添加任务（任务携带自己的引擎）
        framework.addTask(new SampleTask("param1"));
//        framework.addTask(new SampleTask("param2"));

        // 5. 执行任务
        try {
            ExecutionResult result = framework.execute();
            if (result.isSuccess()) {
                System.out.println("All tasks executed successfully");
            } else {
                System.out.println("Some tasks failed: " + result.getError().getMessage());
            }

            // 6. 打印任务统计信息
            System.out.println("\nTask Statistics:");
            framework.getTaskStats().forEach(stats -> {
                System.out.printf("Task ID: %s, Status: %s, Execution Time: %d ms%n",
                        stats.getTaskId(), stats.getStatus(), stats.getEndTime() - stats.getStartTime());
                if (stats.getErrorMessage() != null) {
                    System.out.printf("Error: %s%n", stats.getErrorMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Task execution failed: " + e.getMessage());
        }
    }
}
