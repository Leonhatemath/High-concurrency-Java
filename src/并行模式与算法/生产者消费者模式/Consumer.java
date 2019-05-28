package 并行模式与算法.生产者消费者模式;

import jdk.nashorn.api.tree.TryTree;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author WangXu
 * @date 2019/5/28 0028 - 16:10
 */
public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue;
    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println("start Consumer id=" + Thread.currentThread().getId());
        Random r = new Random();

        try {
            while (true) {
                PCData data = queue.take();
                if (data != null) {
                    int re = data.getData() * data.getData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(), data.getData(), re));
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
