package 并行模式与算法.并行排序.改进插入排序的希尔排序;

import 并行模式与算法.并行排序.奇偶交换排序.oddEvenSortDemo;
import 并行模式与算法.并行排序.奇偶交换排序.oddEvenSortParelDemo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 19:23
 */
public class shellSortParel {
    //跟并行奇偶排序一样，并行希尔排序花费的时间更久，不知道为什么
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < oddEvenSortDemo.num; i++) {
            arr[i] = random.nextInt();
        }
        long a = System.currentTimeMillis();
        pShellSort(arr);
        long b = System.currentTimeMillis();
        System.out.println("并行希尔排序花费的时间:" + (b - a) + "ms");
    }

    static int[] arr = new int[oddEvenSortDemo.num];
    static ExecutorService pool = Executors.newCachedThreadPool();
    public static class shellSortTask implements Runnable {
        int i = 0;
        int h = 0;
        CountDownLatch l;

        public shellSortTask(int i, int h, CountDownLatch l) {
            this.i = i;
            this.h = h;
            this.l = l;
        }

        @Override
        public void run() {
            if (arr[i] < arr[i - h]) {
                int tmp = arr[i - h];
                int j = i - h;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + h] = arr[j];
                    j -= h;
                }
                arr[j + h] = tmp;
            }
            l.countDown();
        }

    }

    public static void pShellSort(int[] arr) throws InterruptedException {
        int h = 1;
        CountDownLatch latch = null;
        while (h <= arr.length / 3) {

        }
        while (h > 0) {
            System.out.println("h = " + h);
            if (h >= 4) {
                latch = new CountDownLatch(arr.length - h);
            }
            for (int i = h; i < arr.length; i++) {
                if (h >= 4) {
                    pool.execute(new shellSortTask(i, h, latch));
                } else {
                    if (arr[i] < arr[i - h]) {
                        int tmp = arr[i];
                        int j = i- h;
                        while (j >= 0 && arr[j] > tmp) {
                            arr[j + h] = arr[j];
                            j -= h;
                        }
                        arr[j + h] = tmp;
                    }
                }
            }
            latch.await();
            h = (h - 1) / 3;
        }
    }
}
