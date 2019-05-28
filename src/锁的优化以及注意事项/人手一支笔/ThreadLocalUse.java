package 锁的优化以及注意事项.人手一支笔;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WangXu
 * @date 2019/5/25 0025 - 10:26
 */
public class ThreadLocalUse {
    static java.lang.ThreadLocal<SimpleDateFormat> tl = new java.lang.ThreadLocal<>();

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        public void run() {
            try {
                if (tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = tl.get().parse("2019-5-25 10:29:" + i % 60);
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
