package JDK并发包.同步控制.重入锁;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WangXu
 * @date 2019/5/22 0022 - 14:52
 */
public class ReenterLock implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            try {
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl = new ReenterLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
