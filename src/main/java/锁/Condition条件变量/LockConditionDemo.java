package 锁.Condition条件变量;

/**
 * @CreateTime: 2025年 06月 24日 14:10
 * @Description:
 * @Author: MR.YU
 */
public class LockConditionDemo {

    public static void main(String[] args) {
        Message msg = new Message();
        MessageProducer producer = new MessageProducer(msg);
        MessageConsumer consumer = new MessageConsumer(msg);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
