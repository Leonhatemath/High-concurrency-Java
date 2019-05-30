package 并行模式与算法.并行无序数组搜索;

import 并行模式与算法.并行排序.奇偶交换排序.oddEvenSortDemo;
import 并行模式与算法.并行排序.改进插入排序的希尔排序.shellSort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WangXu
 * @date 2019/5/30 0030 - 16:48
 */
public class SearchParnel {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int num = oddEvenSortDemo.num;
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            arr[i] = random.nextInt();
        }
        int searchValue = arr[num / 2];
        long a = System.currentTimeMillis();
        int search_index = SearchParnel.pSearch(searchValue);
        long b = System.currentTimeMillis();
        if (search_index == -1) {
            System.out.println("数组中没有这个数：" + num);
        } else {
            System.out.println(num + "在数组的第" + (search_index + 1) + "位上");
        }
        pool.shutdown();
        int new_search_index = -1;
        System.out.println("并行用的时间：" + (b - a) + "ms");
        long c = System.currentTimeMillis();
        shellSort.shellSort(arr);
        int i = 0;
        int j = num - 1;
        while (i <= j) {
            int mid = (j - i) / 2 + i;
            if (arr[mid] < searchValue) {
                i = mid - 1;
            } else if (arr[mid] > searchValue) {
                j = mid + 1;
            } else {
                new_search_index = mid;
                break;
            }
        }
        long d = System.currentTimeMillis();
        System.out.println("希尔排序加二分查找花费的时间：" + (d - c) + "ms");
        System.out.println("位置为：" + (new_search_index + 1));
    }

    static int[] arr = new int[oddEvenSortDemo.num];
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int Thread_Num = 2;
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int searchValue, int beginPos, int endPos) {
        int i = 0;
        for (i = beginPos; i < endPos; i++) {
            //说明其他线程已经找到了目标
            if (result.get() >= 0) {
                return result.get();
            }
            if (arr[i] == searchValue) {
                if (!result.compareAndSet(-1, i)) {
                    //如果比较设置这个操作失败了，说明result已经不是-1了，那么已经被找到了
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable<Integer> {
        int begin,end,searchValue;

        public SearchTask(int searchValue, int begin, int end) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        public Integer call() {
            int re = search(searchValue, begin, end);
            return re;
        }
    }

    public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {
        int subArrSize = arr.length / Thread_Num + 1;
        List<Future<Integer>> re = new ArrayList<>();
        for (int i = 0; i < arr.length; i += subArrSize) {
            int end = i + subArrSize;
            if (end > arr.length) {
                end = arr.length;
            }
            re.add(pool.submit(new SearchTask(searchValue, i, end)));
        }
        for (Future<Integer> fu : re
        ) {
            if (fu.get() >= 0) {
                return fu.get();
            }
        }
        Thread.sleep(100);
        return -1;
    }

}
