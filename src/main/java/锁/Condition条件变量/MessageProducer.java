package 锁.Condition条件变量;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreateTime: 2025年 06月 24日 14:09
 * @Description:
 * @Author: MR.YU
 */
public class MessageProducer implements Runnable{

    private Message message;

    public MessageProducer(Message msg) {
        message = msg;
    }

    @Override
    public void run() {
        produce();
    }

    public void produce() {
        List<String> msgs = new ArrayList<>();
        msgs.add("Begin");
        msgs.add("Msg1");
        msgs.add("Msg2");

        for (String msg : msgs) {
            message.produce(msg);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        message.produce("End");
        message.setEnd(true);
    }

}
