package 并行模式与算法.并行排序.奇偶交换排序;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 18:12
 */
public class oddEvenSortParelDemo {
    //为什么并行的奇偶排序时间花费比串行的多很多？
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        int num = oddEvenSortDemo.num;
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt(num);
        }
        long a = System.currentTimeMillis();
        pOddEvenSort(arr);
        long b = System.currentTimeMillis();
        System.out.println("并行奇偶排序花费的时间:" + (b - a) + "ms");

    }
    static int[] arr = new int[oddEvenSortDemo.num];
    static int exchFlag = 1;
//    static ExecutorService pool = Executors.newCachedThreadPool();

    static synchronized void setExchFlag(int v) {
        exchFlag = v;
    }

    static synchronized int getExchFlag() {
        return exchFlag;
    }

    public static class OddEvenSortTask implements Runnable {
        int i;
        CountDownLatch latch;

        public OddEvenSortTask(int i, CountDownLatch latch) {
            this.i = i;
            this.latch = latch;
        }

        @Override
        public void run() {
            if (arr[i] > arr[i + 1]) {
                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;
                setExchFlag(1);
            }
            latch.countDown();
        }
    }

    public static void pOddEvenSort(int[] arr) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        int start = 0;
        while (getExchFlag() == 1 || start == 1) {
            setExchFlag(0);
            CountDownLatch latch = new CountDownLatch(arr.length / 2 - (arr.length % 2 == 0 ? start : 0));
            for (int i = start; i < arr.length - 1; i += 2) {
                pool.submit(new OddEvenSortTask(i, latch));
            }
            latch.await();
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }
        }
        pool.shutdown();
    }
}
