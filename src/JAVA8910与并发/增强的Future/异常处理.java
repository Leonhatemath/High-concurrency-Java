package JAVA8910与并发.增强的Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author WangXu
 * @date 2019/6/3 0003 - 19:51
 */
public class 异常处理 {
    /*可以使用exceptionally来进行异常处理*/
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .exceptionally(ex ->{
                    System.out.println(ex.toString());
                    return 0;
                    //进行处理并且返回默认值
                })
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        fu.get();
    }
    public static Integer calc(Integer para) {
        return para / 0;
    }
}
