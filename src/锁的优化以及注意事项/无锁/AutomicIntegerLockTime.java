package 锁的优化以及注意事项.无锁;

import jdk.nashorn.api.tree.ForInLoopTree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WangXu
 * @date 2019/5/25 0025 - 11:22
 */
public class AutomicIntegerLockTime {
    static AtomicInteger i = new AtomicInteger();

    public static class AddThreadAtomic implements Runnable {
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i.incrementAndGet();
            }
        }
    }

    private static int k = 0;
    public static ReentrantLock lock = new ReentrantLock();
    public static class AddThreadLock implements Runnable {
        public void run() {
            for (int j = 0; j < 10000; j++) {
                lock.lock();
                k++;
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        long a = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            ts[j] = new Thread(new AddThreadAtomic());
        }
        for (int j = 0; j < 10; j++) {
            ts[j].start();
        }
        for (int j = 0; j < 10; j++) {
            ts[j].join();
        }
        long b = System.currentTimeMillis();
        long totaltime = b - a;
        System.out.println("AutomicIngeter使用的时间：" + totaltime + "ms");
        System.out.println(i);

        Thread[] tk = new Thread[10];
        long c = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            tk[j] = new Thread(new AddThreadLock());
        }
        for (int j = 0; j < 10; j++) {
            tk[j].start();
        }
        for (int j = 0; j < 10; j++) {
            tk[j].join();
        }
        long d = System.currentTimeMillis();
        long totaltimetwo = d - c;
        System.out.println("Lock使用的时间：" + totaltimetwo + "ms");
        System.out.println(k);
    }


}
