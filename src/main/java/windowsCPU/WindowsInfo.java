package windowsCPU;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;

import java.util.List;

/**
 * @CreateTime: 2025年 05月 07日 17:25
 * @Description:
 * @Author: MR.YU
 */
public class WindowsInfo {

    /**
     * 引入
     * <dependency>
     *     <groupId>com.github.oshi</groupId>
     *     <artifactId>oshi-core</artifactId>
     *     <version>6.4.0</version>
     * </dependency>
     */
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();

//        long[] prevTicks = processor.getSystemCpuLoadTicks();
//
//        try {
//            Thread.sleep(1000); // 等待一秒以计算负载变化
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        long[] ticks = processor.getSystemCpuLoadTicks();
//        long userDelta = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
//        long niceDelta = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
//        long sysDelta = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
//        long idleDelta = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
//        long ioWaitDelta = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
//        long totalDelta = userDelta + niceDelta + sysDelta + idleDelta + ioWaitDelta;
//
//        // 计算用户使用率
//        double userUsage = (double) userDelta / totalDelta * 100;
//        // 输出结果
//        System.out.printf("User CPU Usage: %.2f%%\n", userUsage);

        double cpuLoad = processor.getSystemCpuLoad(1); // 0~1 之间，1 表示 100%
        System.out.println("CPU 使用率：" + String.format("%.2f%%", cpuLoad * 100));


//        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();

        long total = memory.getTotal(); // 总内存
        long available = memory.getAvailable(); // 可用内存
        long used = total - available;

        System.out.println("总内存: " + formatBytes(total));
        System.out.println("已使用: " + formatBytes(used));
        System.out.println("使用率: " + String.format("%.2f%%", (double) used / total * 100));



//        SystemInfo systemInfo = new SystemInfo();
        List<HWDiskStore> disks = systemInfo.getHardware().getDiskStores();

        for (HWDiskStore disk : disks) {
            long size = disk.getSize();
            long reads = disk.getReads();
            long writes = disk.getWrites();
            System.out.println("磁盘名称: " + disk.getName());
            System.out.println("容量: " + formatBytes(size));
            System.out.println("读取次数: " + reads);
            System.out.println("写入次数: " + writes);
            System.out.println("使用率: " + String.format("%.2f%%", (double) reads / (reads + writes) * 100));
        }
    }

    private static String formatBytes(long bytes) {
        return (bytes / (1024 * 1024)) + " MB";
    }

}
