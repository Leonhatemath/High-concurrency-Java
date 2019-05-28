package JAVA8910与并发.声明式的;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author WangXu
 * @date 2019/5/28 0028 - 16:48
 */
public class declarative {
    public static void main(String[] args) {
        int[] iArr = {1, 3, 5, 6, 4, 7, 8, 9};
        Arrays.stream(iArr).forEach(System.out::print);
        System.out.println();
        Arrays.stream(iArr).map((x) -> x = x + 1).forEach(System.out::print);
        System.out.println();
        Arrays.stream(iArr).forEach(System.out::print);
    }
}
