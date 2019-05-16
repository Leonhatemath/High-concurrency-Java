package java并行程序基础.线程的基本操作.等待线程结束join和谦让yield;

public class JoinMain {
    public volatile static int i = 0;
    //volatile:被修饰的变量极有可能会被线程修改，为保证可见性因此进行申明

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (; i < 10000; i++) ;
        }
    }

    public static void main(String[] args) throws InterruptedException{
        AddThread at = new AddThread();
        at.start();
        at.join();
        //如果没有join(),在主线程中，at还没有执行，就已经被输出了i=0
        //而在加入join()之后，会等待at执行完，再输出i，这样得到就是i=10000
        System.out.println("i = "+i);
    }
}
