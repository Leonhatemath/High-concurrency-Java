package 并行模式与算法.并行排序.奇偶交换排序;

import java.util.Random;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 17:13
 */
public class oddEvenSortDemo {
    public static int num = 100000;
    public static void main(String[] args) {
        Random random = new Random();

        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt(num);
        }
        long a = System.currentTimeMillis();
        oddEvenSort(arr);
        long b = System.currentTimeMillis();
        System.out.println("串行奇偶排序花费的时间:" + (b - a) + "ms");
    }
    public static void oddEvenSort(int[] arr) {
        int exchFlag = 1,start = 0;
        while (exchFlag == 1 || start == 1) {
            exchFlag = 0;
            for (int i = start; i < arr.length - 1; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    exchFlag = 1;
                }
            }
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }
        }
    }
}
