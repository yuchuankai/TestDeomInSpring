package 锁.Condition条件变量;

/**
 * @CreateTime: 2025年 06月 24日 14:09
 * @Description:
 * @Author: MR.YU
 */
public class MessageConsumer implements Runnable{

    private Message message;

    public MessageConsumer(Message msg) {
        message = msg;
    }

    @Override
    public void run() {
        while (!message.isEnd()) { message.consume(); }
    }

}
