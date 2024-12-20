package Java_io.file_io;

import java.io.*;

/**
 * @CreateTime: 2024年 12月 20日 14:31
 * @Description:
 * @Author: MR.YU
 */
public class FileBioDemo {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\sr\\Desktop\\2024_12\\测试文件(公开).txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("Hello, World!");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
