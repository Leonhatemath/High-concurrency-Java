package JDK并发包.同步控制.重入锁;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WangXu
 * @date 2019/5/22 0022 - 19:06
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + ":获得锁");
            }finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1, "Teread_t1");
        Thread t2 = new Thread(r1, "Teread_t2");
        t1.start();t2.start();
    }
}
