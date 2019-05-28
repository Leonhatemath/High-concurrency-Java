package JDK并发包.同步控制.循环栅栏;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author WangXu
 * @date 2019/5/22 0022 - 20:11
 */
public class CycliBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclic;

        public Soldier(String soldierName, CyclicBarrier cyclic) {
            this.soldier = soldierName;
            this.cyclic = cyclic;
        }

        public void run() {
            try {
                cyclic.await();
                dowork();
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void dowork() throws InterruptedException {
            Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            System.out.println(soldier + ":任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        public void run() {
            if (flag) {
                System.out.println("司令：[士兵" + N + "个，任务完成！");

            } else {
                System.out.println("司令：[士兵" + N + "个，集合完毕！");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSolider = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        //设置屏障点，用于执行里面的方法
        System.out.println("集合队伍!");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "报道！");
            allSolider[i] = new Thread(new Soldier("士兵" + i, cyclic));
        }
    }

}
