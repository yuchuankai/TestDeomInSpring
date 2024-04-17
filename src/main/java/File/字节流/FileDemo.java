/**
 * @Project: testDeomInSpring
 * @ClassName: FileDemo
 * @Date: 2024年 04月 15日 15:11
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package File.字节流;

import org.apache.commons.lang.Validate;

import java.io.*;
import java.util.Arrays;

/**
 * @Description:
 * @Date: 2024年 04月 15日 15:11
 * @Author: MR.Yu
 **/
public class FileDemo {

    public static void main(String[] args) {
        String path = "E:\\你好恢复软件.txt";

        File file = new File(path);
        Validate.isTrue(file.exists(), "文件不存在");
        int length = (int) file.length();
        try {
            InputStream input = new FileInputStream(file);
            byte[] content = new byte[length];
            try {
                input.read(content);
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(content));
            OutputStream output = new FileOutputStream(file);
            try {
                byte[] byteArray = new byte[length];
                Arrays.fill(byteArray, (byte) 1);
                System.out.println(Arrays.toString(byteArray));
                output.write(byteArray);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}