package java并行程序基础.volatile与JMM;

public class NoVisibility {
    private static volatile boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(100);
        //在将数据添加了volatile的修饰之后，线程就会察觉到数据的变化而进行操作
        //否则将一直无法输出number
        number = 42;
        ready = true;
        Thread.sleep(10000);
    }

}
