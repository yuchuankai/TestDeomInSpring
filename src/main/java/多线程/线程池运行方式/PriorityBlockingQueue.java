package 多线程.线程池运行方式;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


/**
 * @CreateTime: 2025年 10月 14日 16:57
 * @Description:
 * @Author: MR.YU
 */
public class PriorityBlockingQueue<E> extends LinkedBlockingDeque<E> {


    public PriorityBlockingQueue(int capacity) {
        super(capacity);
    }

    @Override
    public void addFirst(E e) {
        System.out.println("添加元素：" + e);
        super.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        System.out.println("添加元素：" + e);
        super.addLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        System.out.println("添加元素：" + e);
        return super.offerFirst(e);
    }

    @Override
    public boolean offerLast(E e) {
        System.out.println("添加元素：" + e);
        return super.offerLast(e);
    }

    @Override
    public void putFirst(E e) throws InterruptedException {
        System.out.println("添加元素：" + e);
        super.putFirst(e);
    }

    @Override
    public void putLast(E e) throws InterruptedException {
        System.out.println("添加元素：" + e);
        super.putLast(e);
    }

    @Override
    public boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException {
        System.out.println("添加元素：" + e);
        return super.offerFirst(e, timeout, unit);
    }

    @Override
    public boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException {
        System.out.println("添加元素：" + e);
        return super.offerLast(e, timeout, unit);
    }

    @Override
    public boolean add(E e) {
        System.out.println("添加元素：" + e);
        return super.add(e);
    }

    @Override
    public boolean offer(E e) {
        System.out.println("添加元素：" + e);
        return super.offer(e);
    }

    @Override
    public void put(E e) throws InterruptedException {
        System.out.println("添加元素：" + e);
        super.put(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        System.out.println("添加元素：" + e);
        return super.offer(e, timeout, unit);
    }


}
