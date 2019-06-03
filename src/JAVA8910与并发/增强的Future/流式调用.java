package JAVA8910与并发.增强的Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author WangXu
 * @date 2019/6/3 0003 - 19:45
 */
public class 流式调用 {
    //completablefuture提供了40多个API就是供函数式变成调用的
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        fu.get();
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
