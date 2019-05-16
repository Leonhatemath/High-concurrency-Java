package java并行程序基础.守护线程Daemon;

public class DaemonDemo {
    public static class DaemonT extends Thread {
        public void run() {
            while (true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new DaemonT();
        t.setDaemon(true);//要注意，设置为守护线程要在线程开始之前
        t.start();
        //设置成守护线程之后，主线程（用户线程）结束之后，守护线程就会退出
        //否则该线程会在主线程结束之后仍然一直执行
        Thread.sleep(2000);
    }
}
