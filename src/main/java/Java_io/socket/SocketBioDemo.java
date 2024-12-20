package Java_io.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @CreateTime: 2024年 12月 20日 14:36
 * @Description:
 * @Author: MR.YU
 */
public class SocketBioDemo {
    public static void main(String[] args) {
        // server
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                // 阻塞等待客户端连接
                while (true) {
                    System.out.println("等待客户端连接.......");
                    Socket accept = serverSocket.accept();
                    System.out.println("客户端连接成功.......");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String line;
                    // reader.readLine() 会阻塞执行，知道客户端主动断开连接
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.println("客户端关闭连接.......");
                    reader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "客户端发送消息");
                Socket socket = new Socket("127.0.0.1", 8080);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("Hello, World! ====================");
                writer.newLine();
                writer.flush();
                writer.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "客户端发送消息");
                Socket socket = new Socket("127.0.0.1", 8080);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("Hello, World! -----------------");
                writer.newLine();
                writer.flush();
                writer.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
