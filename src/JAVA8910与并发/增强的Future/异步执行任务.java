package JAVA8910与并发.增强的Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author WangXu
 * @date 2019/6/3 0003 - 19:42
 */
public class 异步执行任务 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(50));
        System.out.println(future.get());
        //这里如果没有得到结果，就会一直等待
    }

    public static Integer calc(Integer para) {
        try {
            Thread.sleep(1000);
            //模拟长时间的一个操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }
}
