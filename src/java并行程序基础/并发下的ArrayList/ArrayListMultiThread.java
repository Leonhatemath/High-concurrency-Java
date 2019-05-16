package java并行程序基础.并发下的ArrayList;

import java.util.ArrayList;

public class ArrayListMultiThread {
    static ArrayList<Integer> al = new ArrayList<>(10);

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                al.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(al.size());
        /*三种结果：
        1.正常size=2000000
        2.size<2000000并且没有报错：
        这是因为多线程访问冲突，两个线程对al的同一个位置赋值导致的
        3.抛出异常报错：
        这是因为al在扩容的时候，因为没有锁，内部一致性被破坏，
        两个线程访问到了不一致的内部状态导致出现了越界的问题*/
    }
}
