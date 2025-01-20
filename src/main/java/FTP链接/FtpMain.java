package FTP链接;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @CreateTime: 2025年 01月 20日 9:15
 * @Description:
 * @Author: MR.YU
 */
public class FtpMain {

    public static void main(String[] args) {
        StandardFtpHelper ftpHelper = new StandardFtpHelper();
        ftpHelper.loginFtpServer("10.0.47.74", "ftpuser", "1qaz!QAZ123,,", 21, 1000);
        OutputStream outputStream = ftpHelper.getOutputStream("/var/ftp/pub/ftpuser/test/yu.txt");

       try( BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
           writer.write("Hello, World! ====================");
           writer.flush();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           try {
               outputStream.close();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }

    }
}
