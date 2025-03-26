/**
 * @Project: testDeomInSpring
 * @ClassName: FileChange
 * @Date: 2024年 05月 06日 14:23
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 字符串转化;

import lombok.SneakyThrows;
import 文件操作.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Date: 2024年 05月 06日 14:23
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) throws UnsupportedEncodingException {

//        test("test", "range");
//        test2("公开");
//        test3("Hello, World!"); // 纯ASCII字符
//        test3("你好，世界！"); // 纯中文字符
//        test3("Hello, 你好！"); // 混合字符
//        test3("😊"); // 表情符号，GBK无法编码


        // 测试转换编码
//        String original = "公开";
//        String converted = convertToGBK(original);
//        System.out.println("Original: " + original);
//        System.out.println("Converted: " + converted);

        test4("D:\\tmp\\test\\test");

    }


    // 字符串格式化
    private static void test(String tableName, String range) {
        System.out.println(String.format("DELETE FROM %s WHERE %s", tableName, range));
    }

    private static void test2(String a) throws UnsupportedEncodingException {

        byte[] bytes = a.getBytes("UTF-8");
//        for (int i = 0; i < bytes.length; i++) {
//            if (bytes[i] < 0) {
//                bytes[i] = (byte) (bytes[i] * -1);
//            }
//        }
        System.out.println(bytes.length);
        System.out.println(new String(bytes, "GBK"));
    }

    private static void test3(String a) {
        System.out.println(Charset.forName("GBK").newEncoder().canEncode(a));
    }

    @SneakyThrows
    private static void test4(String path) {
        File file = FileUtils.openFile(path);
        File[] files = file.listFiles();
        String s = DetectOS();

        assert files != null;
        for (File file1 : files) {
            if (s.equals("windows")) {

            } else {
                Process exec = Runtime.getRuntime().exec("file -bi " + file1.getName());
                BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }


            System.out.println(file1.getName());
        }
    }



    public static String DetectOS() {
        // 获取操作系统名称
        String osName = System.getProperty("os.name").toLowerCase();

        // 判断操作系统类型
        if (osName.contains("win")) {
            return "Windows";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return "Linux";
        } else if (osName.contains("mac")) {
            return "Mac";
        }
        return "Unknown";
    }


    private static String convertToGBK(String input) throws UnsupportedEncodingException {
        if (Charset.forName("GBK").newEncoder().canEncode(input)) {
            byte[] bytes = input.getBytes("UTF-8");
            return new String(bytes, "GBK");
        } else {
            // 处理无法编码的字符
            // 例如，替换为问号或其他占位符
            StringBuilder result = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (Charset.forName("GBK").newEncoder().canEncode(c)) {
                    result.append(c);
                } else {
                    result.append('?'); // 替换为问号
                }
            }
            return result.toString();
        }
    }
}
