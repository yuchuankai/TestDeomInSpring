package Java_io.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @CreateTime: 2024年 12月 20日 15:13
 * @Description:
 * @Author: MR.YU
 */
public class SocketNioDemo {

    public static void main(String[] args) {
        new Thread(SocketNioDemo::server).start();
        new Thread(SocketNioDemo::client1).start();
        new Thread(SocketNioDemo::client2).start();
    }

    public static void server() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8081));

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.wrap("Hello, Client!".getBytes());
                        client.write(buffer);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        client.read(buffer);
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        System.out.println("Client says: " + new String(bytes));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void client1() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8081));
            socketChannel.configureBlocking(false);
            while (true) {
                ByteBuffer buffer = ByteBuffer.wrap("client1-Hello, Server!".getBytes());
                socketChannel.write(buffer);
                buffer.clear();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void client2() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8081));
            socketChannel.configureBlocking(false);
            while (true) {
                ByteBuffer buffer = ByteBuffer.wrap("client2-Hello, Server!".getBytes());
                socketChannel.write(buffer);
                buffer.clear();
                Thread.sleep(1000*3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
