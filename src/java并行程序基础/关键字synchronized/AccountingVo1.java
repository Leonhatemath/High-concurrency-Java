package java并行程序基础.关键字synchronized;

public class AccountingVo1 implements Runnable {
    static AccountingVo1 instance = new AccountingVo1();
    static volatile int i = 0;

    public static void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
        //可以看到两个线程没有同步数据，最后的结果也不是20000000
    }
}
