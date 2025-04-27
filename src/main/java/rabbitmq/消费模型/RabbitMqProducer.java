package rabbitmq.消费模型;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import lombok.SneakyThrows;
import rabbitmq.RabbitMqUtil;

/**
 * @CreateTime: 2025年 04月 09日 9:31
 * @Description:
 * @Author: MR.YU
 */
public class RabbitMqProducer {

    @SneakyThrows
    public static void main(String[] args) {
        Connection connection = RabbitMqUtil.getConnection();

        if (connection == null) {
            System.out.println("连接失败");
            return;
        }

        Channel channel = connection.createChannel();

        // 通道绑定对应消息队列,生成者和消费者这个方法的参数要严格一致才能消费到
        // 参数1 队列名称 ，此处不能决定发往哪个队列，需要下面basicPublish第二个参数决定
        // 参数2 用来定义队列特性是否要持久化 true 持久化 false 不持久化，此处只是队列的持久化，并不是消息的持久化，消息的持久化需要设置下面basicPublish方法的第三个参数
        // 参数3 exclusive 是否独占队列 true 独占队列 false 不独占队列
        // 参数4 autoDelete 是否在消费完成后自动删除，true 自动删除 false 不自动删除,消费者只有关闭连接后队列才会删除
        // 参数5 额外附加参数
        channel.queueDeclare("topic",true,false,true,null);
        // 发布消息 参数1 交换机名 参数2 队列名 参数3传递消息的额外设置 配合queueDeclare方法的地二个参数设置可以设置消息的持久化， 参数4 消息内容
        for (int i = 0; i < 100; i++) {
            channel.basicPublish("","topic", MessageProperties.PERSISTENT_TEXT_PLAIN,("hellom" + i).getBytes());
        }

        RabbitMqUtil.closeConnection(channel, connection);
    }
}
