package JAVA8910与并发.增强的Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author WangXu
 * @date 2019/6/3 0003 - 20:01
 */
public class 增加timeout功能 {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return calc(50);
        }).orTimeout(1, TimeUnit.SECONDS).exceptionally(e -> {
            System.err.println(e);
            return 0;
        }).thenAccept(System.out::println);

        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static Integer calc(Integer para) {
        return para / 2;
    }
}
