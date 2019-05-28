/**
 * @author WangXu
 * @date 2019/5/25 0025 - 9:43
 */
public class Test {
    public static void main(String[] args) {
        int i = 0;
        int length = 5;
        while (i < length) {
            System.out.println((++i == length) ? 0 : i);
        }
    }
}
