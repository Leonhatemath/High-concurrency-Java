package 并行模式与算法.并行排序.改进插入排序的希尔排序;

import 并行模式与算法.并行排序.奇偶交换排序.oddEvenSortDemo;

import java.util.Random;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 18:49
 */
public class shellSort {
    public static void main(String[] args) {
        int num = oddEvenSortDemo.num;
        Random random = new Random();
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt();
        }
        long a = System.currentTimeMillis();
        shellSort(arr);
        long b = System.currentTimeMillis();
        System.out.println("串行希尔排序花费的时间:" + (b - a) + "ms");

    }
    public static void shellSort(int[] arr) {
        int h = 1;
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                if (arr[i] < arr[i - h]) {
                    int tmp = arr[i];
                    int j = i - h;
                    while (j >= 0 && arr[j] > tmp) {
                        arr[j + h] = arr[j];
                        j -= h;
                    }
                    arr[j + h] = tmp;
                }
            }
            h = (h - 1) / 3;
        }
    }
}
