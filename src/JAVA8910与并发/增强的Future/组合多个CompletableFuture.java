package JAVA8910与并发.增强的Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author WangXu
 * @date 2019/6/3 0003 - 19:54
 */
public class 组合多个CompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //用compose接口进行多个组合
        CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc(i))
                        .thenApply((str) -> "\"" + str + "\"").thenAccept(System.out::println));
        fu.get();
        //用combine进行组合,把两个completableFuture的结果结合起来进行运算
        CompletableFuture<Integer> intFuture = CompletableFuture.supplyAsync(() -> calc(50));
        CompletableFuture<Integer> intFuture2 = CompletableFuture.supplyAsync(() -> calc(25));

        CompletableFuture<Void> fu1 = intFuture.thenCombine(intFuture2, (i, j) -> (i + j))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        fu1.get();
    }
    public static Integer calc(Integer para) {
        return para / 2;
    }
}
