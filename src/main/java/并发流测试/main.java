package 并发流测试;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @CreateTime: 2024年 08月 30日 16:02
 * @Description:
 * @Author: MR.YU
 */
public class main {


    public static void main(String[] args) {
        Integer fileNum = 1001;
        Integer startPageNum = 1;
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MyRecursiveAction(startPageNum, fileNum));
    }


    private static class MyRecursiveAction extends RecursiveAction {

        //开始页码
        private Integer startPageNum;

        //每次拆分左1w, 剩余条数
        private Integer fileNum;

        //每页条数，单次接口查询数据
        private final Integer pageSize = 10;

        MyRecursiveAction(Integer startPageNum, Integer fileNum) {
            this.startPageNum = startPageNum;
            this.fileNum = fileNum;
        }

        @Override
        protected void compute() {

            if (fileNum > pageSize) {
                Integer tmpNum  = fileNum - pageSize;

                MyRecursiveAction leftAction = new MyRecursiveAction(startPageNum, pageSize);
                leftAction.fork();

                MyRecursiveAction rightAction = new MyRecursiveAction(startPageNum + 1, tmpNum);
                rightAction.fork();
                // 等待子任务执行完成
                leftAction.join();
                rightAction.join();
                return;
            }

            System.out.println("开始页码：" + startPageNum + "，当前页码数量：" + fileNum);
        }
    }
}
