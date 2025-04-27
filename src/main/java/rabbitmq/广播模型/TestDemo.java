package rabbitmq.广播模型;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import rabbitmq.RabbitMqUtil;

import java.io.IOException;

/**
 * @CreateTime: 2025年 04月 09日 9:52
 * @Description:
 * @Author: MR.YU
 */
public class TestDemo {


    public static void main(String[] args) throws IOException {

        consumer();
        consumer();
        consumer();
        consumer();
        producer("aaa");
        producer("bbb");
        producer("ccc");

    }


    private static void producer(String massage) throws IOException {

        Connection connection = RabbitMqUtil.getConnection();

        if (connection == null) {
            System.out.println("连接失败");
            return;
        }

        Channel channel = connection.createChannel();
        // 将通道指定交换机，参数1交换机名称 参数2 交换机类型   fanout 广播类型 参数固定为fanout
        channel.exchangeDeclare("exchange.fanout", "fanout");
        // 发送消息
        channel.basicPublish("exchange.fanout", "", null, massage.getBytes());
        // 释放资源
        RabbitMqUtil.closeConnection(channel, connection);

    }

    private static void consumer() throws IOException {
        Connection connection = RabbitMqUtil.getConnection();

        if (connection == null) {
            System.out.println("连接失败");
            return;
        }

        Channel channel = connection.createChannel();
        // 将通道指定交换机，参数1交换机名称 参数2 交换机类型   fanout 广播类型 参数固定为fanout
        channel.exchangeDeclare("exchange.fanout", "fanout");
        // 临时队列
        String queueName = channel.queueDeclare().getQueue();

        // 绑定队列到交换机
        channel.queueBind(queueName, "exchange.fanout", "");

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumer: " + new String(body));
            }
        });
    }
}
