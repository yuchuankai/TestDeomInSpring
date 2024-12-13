package 程序关闭钩子;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @CreateTime: 2024年 11月 27日 16:14
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) throws InterruptedException {

        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    CompletableFuture<Void> future =
                                            CompletableFuture.runAsync(
                                                    () -> {
                                                        System.out.println("关闭");
                                                    });
                                    try {
                                        future.get(15, TimeUnit.SECONDS);
                                    } catch (Exception e) {
                                    }
                                }));


        System.out.println("start");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println("end");

    }
}
