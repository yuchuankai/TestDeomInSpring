package 监听文件夹变化;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @CreateTime: 2024年 07月 25日 9:47
 * @Description:
 * @Author: MR.YU
 */
public class FileChange {


    public static void main(String[] args)  {

        Date date = new Date();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Date date1 = new Date();
        System.out.println();
        System.out.println((date1.getTime()-date.getTime())/1000.00);
    }

    private static final SmbConfig config = SmbConfig.builder().withTimeout(120, TimeUnit.SECONDS)
            .withTimeout(120, TimeUnit.SECONDS) // 超时设置读，写和Transact超时（默认为60秒）
            .withSoTimeout(180, TimeUnit.SECONDS) // Socket超时（默认为0秒）
            .build();
    static void WatchDirForSMB() throws IOException {

        SMBClient client = new SMBClient(config);
        Connection connection = client.connect("10.0.63.54");
        AuthenticationContext ac = new AuthenticationContext("Administrator", "chuan".toCharArray(), "DESKTOP-IK7FD6B");
        Session session = connection.authenticate(ac); // 执行权限认证
        DiskShare diskShare = (DiskShare) session.connectShare("share");
    }

    public static void traverseFolder(Path path) {
        try (Stream<Path> paths = Files.walk(path,1000)) {
            paths.forEach(p -> {
                try {
                    BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                    if (attr.isDirectory()) {
                        // 如果是目录，可以打印目录名，或者进一步处理
                        System.out.println("Directory: " + p);
                    } else {
                        // 如果是文件，打印文件路径
                        System.out.println("File: " + p);
                        System.out.println(attr.size());
                        System.out.println(attr.lastModifiedTime());
                        System.out.println(p.getFileName());
                    }
                } catch (IOException e) {
                    // 处理异常，例如权限问题
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            // 处理遍历过程中可能发生的异常，如路径不存在
            e.printStackTrace();
        }
    }

    static void WatchDirForLocal()throws IOException, InterruptedException{
        // 替换为你的目录路径
        Path dir = Paths.get("Z:\\");

        // 获取文件系统的WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // 注册目录，并指定要监听的事件类型
        dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        // 无限循环等待事件发生
        while (true) {
            // 等待事件发生
            WatchKey key = watchService.take();

            // 遍历所有事件
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // 事件上下文，即发生变化的文件或目录
                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                System.out.println(kind.name() + ": " + fileName);
            }

            // 重置key，以便它可以再次用于接收事件
            boolean valid = key.reset();
            if (!valid) {
                break; // 循环将退出
            }
        }
    }
}
