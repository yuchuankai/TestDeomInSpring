package java执行脚本;

import com.taosdata.jdbc.utils.OSUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @CreateTime: 2025年 09月 22日 15:22
 * @Description:
 * @Author: MR.YU
 */
public class ShellStart {

    public static void main(String[] args) {

        ProcessBuilder processBuilder = new ProcessBuilder();

        List<String> command = new LinkedList<>();

        command.add("cmd.exe");

        if(OSUtils.isWindows()){
            command.add("/c");
        }
        command.add("dir");
//        command.add("ECHO 'hello world'");


        processBuilder.command(command);

        try {
            Process start = processBuilder.start();

            // 读取并打印输出结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(start.getInputStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待进程结束并获取退出码
            int exitCode = start.waitFor();
            System.out.println("Process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
