package JDK并发包.线程池.固定大小的线程池;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WangXu
 * @date 2019/5/22 0022 - 21:05
 */
public class ThreadPoolDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        ExecutorService ex = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            ex.execute(task);
        }
        ex.shutdown();
    }
}
