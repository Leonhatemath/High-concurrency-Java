package 锁的优化以及注意事项.人手一支笔;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WangXu
 * @date 2019/5/25 0025 - 10:16
 */
public class ThreadLocal {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ReentrantLock lock = new ReentrantLock();
    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        public void run() {
            try {
                lock.lock();
                Date t = sdf.parse("2019-5-25 10:18:" + i % 60);
                lock.unlock();
                System.out.println(i + ":" + t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            es.execute(new ParseDate(i));
        }
        es.shutdown();
    }
}
