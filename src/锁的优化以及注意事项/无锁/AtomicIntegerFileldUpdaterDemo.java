package 锁的优化以及注意事项.无锁;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author WangXu
 * @date 2019/5/25 0025 - 15:30
 */
public class AtomicIntegerFileldUpdaterDemo {
    public static class Candidate {
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> ScoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu = new Candidate();
        Thread[] t = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            t[i] = new Thread(){
                public void run() {
                    if (Math.random() > 0.4) {
                        ScoreUpdater.incrementAndGet(stu);
                        allScore.incrementAndGet();
                    }
                }
            };
            t[i].start();
        }
        for (int i = 0; i < 10000; i++) {
            t[i].join();
        }
        System.out.println("score=" + stu.score);
        System.out.println("allScore=" + allScore);

    }
}
