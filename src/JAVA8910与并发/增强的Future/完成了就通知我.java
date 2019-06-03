package JAVA8910与并发.增强的Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author WangXu
 * @date 2019/6/3 0003 - 19:35
 */
public class 完成了就通知我 {
    /*修饰过的数据可以在并发计算过程中等待，然后手动的让真实数据内容输入
    * 并标记完成状态，future.complete(num)
    * 这样就可以做到完成了就通知我*/
    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new AskThread(future)).start();
        Thread.sleep(1000);
        future.complete(60);
        //此时才输入真实的数据,并标记状态为完成
    }
    public static class AskThread implements Runnable{
        CompletableFuture<Integer> re = null;

        public AskThread(CompletableFuture<Integer> re) {
            this.re = re;
        }

        @Override
        public void run() {
            int myRe = 0;
            try {
                myRe = re.get() * re.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(myRe);
        }
    }
}
