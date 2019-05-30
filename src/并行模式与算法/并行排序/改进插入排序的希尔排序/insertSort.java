package 并行模式与算法.并行排序.改进插入排序的希尔排序;

import 并行模式与算法.并行排序.奇偶交换排序.oddEvenSortDemo;

import java.util.Random;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 18:40
 */
public class insertSort {
    public static void main(String[] args) {
        int num = oddEvenSortDemo.num;
        Random random = new Random();
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt();
        }
        long a = System.currentTimeMillis();
        insertSort(arr);
        long b = System.currentTimeMillis();
        System.out.println("串行插入排序花费的时间:" + (b - a) + "ms");
    }
    //插入排序
    public static void insertSort(int[] arr) {
        int length = arr.length;
        int i,j,key;
        for (i = 1; i < length; i++) {
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j --;
            }
            arr[j + 1] = key;
        }
    }
}
