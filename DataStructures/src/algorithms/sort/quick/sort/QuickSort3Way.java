package algorithms.sort.quick.sort;

import algorithms.sort.SortTestHelper;
import algorithms.sort.insertion.sort.InsertionSort;

public class QuickSort3Way {

    // 我们的算法类不允许产生任何实例
    private QuickSort3Way(){}

    // 递归使用快速排序,对arr[l...r]的范围进行排序
    private static void sort(Comparable[] arr, int l, int r){

        if( r - l <= 15 ){
            InsertionSort.sort(arr, l, r);
            return;
        }


        swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);

        Comparable v = arr[l];

        int lt = l,gt = r + 1, i = l + 1;
        while (i < gt) {
            if (arr[i].compareTo(v) < 0) {
                swap(arr, i, lt + 1);
                i++;
                lt++;
            } else if (arr[i].compareTo(v) > 0) {
                swap(arr, i, gt - 1);
                gt--;
            } else {
                i++;
            }
        }
        swap(arr, l, lt--); // i 有一定可能性出现在外面，即 i = r + 1，而j最小只会为 l，所以只能选j，而且最重要的是j是在上一步swap操作后比v小的索引，只有比v小的元素才能往前和v交换，此时用i显然i对应的元素是大于v的不能互换，但应该可以用i - 1
        sort(arr, l, lt);
        sort(arr, gt, r);
    }

    public static void sort(Comparable[] arr){

        int n = arr.length;
        sort(arr, 0, n-1);
    }

    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // 测试 QuickSort
    public static void main(String[] args) {

        // Quick Sort也是一个O(nlogn)复杂度的算法
        // 可以在1秒之内轻松处理100万数量级的数据
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 1000000);
        SortTestHelper.testSort("algorithms.sort.quick.sort.QuickSort3Way", arr);

    }
}