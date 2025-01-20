/**
 * @Project: testDeomInSpring
 * @ClassName: PipedStreamDemo
 * @Date: 2024年 04月 16日 9:20
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Java_io.File.管道流;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @Description: 线程通信
 * @Date: 2024年 04月 16日 9:20
 * @Author: MR.Yu
 **/
public class PipedStreamDemo {

    public static void main(String[] args) {
        WriteThread writeThread = new WriteThread();
        ReadThread readThread = new ReadThread();
        try {
            writeThread.getWriter().connect(readThread.getReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(writeThread).start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(readThread).start();
    }


    static class WriteThread implements Runnable {

        private PipedOutputStream writer;

        public WriteThread() {
            this.writer = new PipedOutputStream();
        }

        @Override
        public void run() {
            try {
                int i = 100;
                while (i > 0) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("写入数据" + i);
//                    writer.close();
                    writer.write(String.valueOf(i).getBytes());

                    i--;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public PipedOutputStream getWriter() {
            return writer;
        }
    }


    static class ReadThread implements Runnable {

        private PipedInputStream reader;

        public ReadThread() {
            this.reader = new PipedInputStream();
        }

        @Override
        public void run() {
            byte[] b = new byte[1024];
            int len = 0;
            System.out.println("读取数据");
            try {
                len = reader.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(b, 0, len));
        }

        public PipedInputStream getReader() {
            return reader;
        }
    }

}
