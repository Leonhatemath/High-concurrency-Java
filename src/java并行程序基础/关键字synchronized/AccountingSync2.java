package java并行程序基础.关键字synchronized;

public class AccountingSync2 implements Runnable {
    static AccountingSync2 instance = new AccountingSync2();
    static int i = 0;

    public synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
        //把调用的方法直接用synchronized修饰就可以了
        //要注意的是，传入线程的必须是同一个实体，也就19，20的instace要是同一个
        /*如果不是同一个实体，那么方法increase()就应该用static静态化，
        这样不同实体访问的increase()仍然是同步的同一个方法*/

    }
}
