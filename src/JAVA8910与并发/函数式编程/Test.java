package JAVA8910与并发.函数式编程;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @author WangXu
 * @date 2019/5/29 0029 - 20:03
 */
public class Test {
    static int[] arr = {1, 3, 4, 5, 6, 7, 8, 9, 10};
    public static void main(String[] args) {
        //one
        Arrays.stream(arr).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });
        //two
        Arrays.stream(arr).forEach((final int x)->{
            System.out.println(x);
        });
        //three
        Arrays.stream(arr).forEach((x)->{
            System.out.println(x);
        });
        //four
        Arrays.stream(arr).forEach((x)-> System.out.println(x));
        //five
        Arrays.stream(arr).forEach(System.out::println);
    }
}
