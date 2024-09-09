/**
 * @Project: testDeomInSpring
 * @ClassName: FileChange
 * @Date: 2024年 05月 06日 14:24
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package JVM;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Date: 2024年 05月 06日 14:24
 * @Author: MR.Yu
 **/
public class main {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            System.out.println("线程开始执行");
            countDownLatch.countDown();
        });
        Runtime.getRuntime().addShutdownHook(thread);
        Thread.sleep(3000);
        System.out.println("主线程执行完毕");
    }
}
