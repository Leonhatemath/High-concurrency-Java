package java并行程序基础.错误的加锁;

public class BadLockOnInteger implements Runnable {
    public static Integer i = 0;
    static BadLockOnInteger instance = new BadLockOnInteger();

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            synchronized (i) {
                //在java中，基本类型的变化，
                // 都是赋予新值然后再把新值的引用给指向的i
                //所以不能用i来作为锁，
                // 否则不同线程的锁很大概率不是一个对象
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
