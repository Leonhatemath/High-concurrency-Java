package JAVA8910与并发.函数式编程;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author WangXu
 * @date 2019/5/29 0029 - 20:20
 */
public class TestThree {
    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        System.out.println(IntStream.range(1, 1000000).filter(TestThree::isPrime).count());
        long b = System.currentTimeMillis();
        System.out.println("串行花费的时间：" + (b - a) + "ms");
        long c = System.currentTimeMillis();
        System.out.println(IntStream.range(1, 1000000).parallel().filter(TestThree::isPrime).count());
        long d = System.currentTimeMillis();
        System.out.println("并行花费的时间：" + (d - c) + "ms");
    }

    public static boolean isPrime(int number) {
        int tmp = number;
        if (tmp < 2) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(tmp); i++) {
            if (tmp % i == 0) {
                return false;
            }
        }
        return true;
    }

}
