package windowsCPU;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @CreateTime: 2025年 05月 08日 12:00
 * @Description:
 * @Author: MR.YU
 */
public class DiskUsage {

    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem os = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hal = systemInfo.getHardware();

        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();

        long totalDiskSpace = 0;
        long usedDiskSpace = 0;

        for (OSFileStore fs : fileStores) {
            long total = fs.getTotalSpace();
            long usable = fs.getUsableSpace();
            long used = total - usable;
            totalDiskSpace += total;
            usedDiskSpace += used;
            double usagePercent = (total > 0) ? (double) used / total * 100 : 0;

            System.out.println("驱动器: " + fs.getMount());
            System.out.println("文件系统类型: " + fs.getType());
            System.out.printf("总空间: %.2f GB%n", total / 1e9);
            System.out.printf("可用空间: %.2f GB%n", usable / 1e9);
            System.out.printf("已用空间: %.2f GB%n", used / 1e9);
            System.out.printf("使用率: %.2f%%%n", usagePercent);
            System.out.println("-----------------------------");
        }

        double usagePercent = (totalDiskSpace > 0) ? (double) usedDiskSpace / totalDiskSpace * 100 : 0;
        System.out.printf("使用率: %.2f%%%n", usagePercent);
    }
}
