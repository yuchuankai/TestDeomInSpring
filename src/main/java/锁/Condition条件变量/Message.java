package 锁.Condition条件变量;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @CreateTime: 2025年 06月 24日 14:05
 * @Description:
 * @Author: MR.YU
 */
public class Message {

    private final Lock lock = new ReentrantLock();

    private final Condition producerCondition = lock.newCondition();

    private final Condition consumerCondition = lock.newCondition();

    private String message;

    private boolean state;

    private boolean end;


    public void consume() {
        //lock
        lock.lock();
        try {
            System.out.println("consume");
            // no new message wait for new message
            while (!state) { consumerCondition.await();
                System.out.println("no new message");}

            System.out.println("consume message : " + message);
            state = false;
            // message consumed, notify waiting thread
            producerCondition.signal();
        } catch (InterruptedException ie) {
            System.out.println("Thread interrupted - viewMessage");
        } finally {
            lock.unlock();
        }
    }

    public void produce(String message) {
        lock.lock();
        try {
            System.out.println("produce");
            // last message not consumed, wait for it be consumed
            while (state) { producerCondition.await();
                System.out.println("last message not consumed");}

            System.out.println("produce msg: " + message);
            this.message = message;
            state = true;
            // new message added, notify waiting thread
            consumerCondition.signal();
        } catch (InterruptedException ie) {
            System.out.println("Thread interrupted - publishMessage");
        } finally {
            lock.unlock();
        }
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
