package rabbitmq.消费模型;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import rabbitmq.RabbitMqUtil;

import java.io.IOException;

/**
 * @CreateTime: 2025年 04月 09日 9:37
 * @Description:
 * @Author: MR.YU
 */
public class RabbitConsumer {

    public static void main(String[] args) {
        consumer2();
    }


    //消费者代码 - 自动确认消息
    public static void consumer1() {
        Connection connection = RabbitMqUtil.getConnection();
        if (connection == null) {
            System.out.println("连接失败");
            return;
        }
        try {
            Channel channel = connection.createChannel();
            // 通道绑定对象
            channel.queueDeclare("topic", true, false, true, null);

            // 消费消息 参数1 队列名称 参数2 开始消息的自动确认机制 参数3 消费时的回调接口
            channel.basicConsume("topic", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("consumer1: " + new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //消费者代码 - 手动确认消息
    public static void consumer2() {
        Connection connection = RabbitMqUtil.getConnection();
        if (connection == null) {
            System.out.println("连接失败");
            return;
        }
        try {
            Channel channel = connection.createChannel();
            // 通道绑定对象
            channel.queueDeclare("topic", true, false, true, null);

            // 消费消息 参数1 队列名称 参数2 开始消息的自动确认机制 参数3 消费时的回调接口
            channel.basicConsume("topic", false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("consumer2: " + new String(body));
                    // 参数1 ：确认队列中哪个具体消息 ，参数2： 是否开启多个消息同时确认，true，可以同时处理信道中的多条消息确认，false 处理完成后确认当前消息消费完成
                    channel.basicAck(envelope.getDeliveryTag(),false); // 单条消息处里确认
                    // channel.basicReject(envelope.getDeliveryTag(),false); // 拒绝改消息，不处理该消息，第二个参数为是否重回队列，false为不重新回到队列，true为重新回到队列，消息会再次被消费，一般设置为false
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
