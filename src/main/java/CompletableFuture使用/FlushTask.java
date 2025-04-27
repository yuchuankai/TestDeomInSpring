package CompletableFuture使用;

/**
 * @CreateTime: 2025年 04月 11日 15:24
 * @Description:
 * @Author: MR.YU
 */
public class FlushTask implements Runnable {

    private String name;

    public FlushTask(String name) {
        this.name = name;
        new Thread(this).start();
    }


    public void flush() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " flush");
         }
    }

    @Override
    public void run() {
        flush();
    }
}
