package Java_io.file_io;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @CreateTime: 2024年 12月 20日 15:05
 * @Description:
 * @Author: MR.YU
 */
public class FileNioDemo {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\sr\\Desktop\\2024_12\\测试文件(公开).txt";
        Path path = Paths.get(filePath);
        String content = "Hello, World!";
        try {
            FileChannel channel = FileChannel.open(path,StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            ByteBuffer buffer = ByteBuffer.allocate(content.length());
            buffer.put(content.getBytes());
            buffer.flip();
            channel.write(buffer);
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileChannel channel = FileChannel.open(path,StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
