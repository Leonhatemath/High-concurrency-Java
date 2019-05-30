package JAVA8910与并发.函数式编程;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @author WangXu
 * @date 2019/5/29 0029 - 20:15
 */
public class TestTwo {
    static int[] arr = {1, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) {
        IntConsumer outprintln = System.out::println;
        IntConsumer errprintln = System.err::println;
        Arrays.stream(arr).forEach(outprintln.andThen(errprintln));

    }
}
