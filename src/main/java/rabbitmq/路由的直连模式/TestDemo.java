package rabbitmq.路由的直连模式;

import com.rabbitmq.client.*;
import rabbitmq.RabbitMqUtil;

import java.io.IOException;

/**
 * @CreateTime: 2025年 04月 09日 10:18
 * @Description:
 * @Author: MR.YU
 */
public class TestDemo {

    public static void main(String[] args) throws IOException {

        consumer("info");
        consumer("warn");
        producer("hello");
    }


    private static void producer(String massage) throws IOException {

        // 创建连接
        Connection connection = RabbitMqUtil.getConnection();
        if (connection == null) {
            return;
        }
        // 获取连接通道
        Channel channel = connection.createChannel();
        // 将通道指定交换机，参数1交换机名称 参数2 交换机类型   direct路由类型  direct固定写死
        channel.exchangeDeclare("logs_direct", "direct");
        // 发送消息
        String routingKey = "info";
        channel.basicPublish("logs_direct", routingKey, null, massage.getBytes());
        // 释放资源
        RabbitMqUtil.closeConnection(channel, connection);

    }


    private static void consumer(String routingKey) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        if (connection == null) {
            System.out.println("连接失败");
            return;
        }

        // 获取连接通道
        Channel channel = connection.createChannel();
        // 通道绑定交换机
        channel.exchangeDeclare("logs_direct","direct");
        // 临时队列
        String queue = channel.queueDeclare().getQueue();
        // 绑定临时队列和交换机，可同时绑定多个
        channel.queueBind(queue,"logs_direct",routingKey);
//        channel.queueBind(queue,"logs_direct","warn");
        // 消费消息 参数1 队列名称 参数2 开始消息的自动确认机制 参数3 消费时的回调接口
        channel.basicConsume(queue,true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumer: " + new String(body));
            }
        });

    }
}
