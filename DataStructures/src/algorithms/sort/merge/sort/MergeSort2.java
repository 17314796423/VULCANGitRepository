package algorithms.sort.merge.sort;

import algorithms.sort.SortTestHelper;
import algorithms.sort.insertion.sort.InsertionSort;

import java.util.*;

// 优化的Merge Sort算法
public class MergeSort2 {

    public static void sort(Comparable[] arr) {
        _mergeSort(arr, 0, arr.length - 1);
    }

    // 递归使用归并排序,对arr[l...r]的范围进行排序
    private static void _mergeSort(Comparable[] arr, int l, int r) {
        // 优化2: 对于小规模数组, 使用插入排序
        if( r - l <= 15 ){
            InsertionSort.sort(arr, l, r);
            return;
        }
        int mid = l + (r - l) / 2;
        _mergeSort(arr, l, mid);
        _mergeSort(arr, mid + 1, r);

        // 优化1: 对于arr[mid] <= arr[mid+1]的情况,不进行merge
        // 对于近乎有序的数组非常有效,但是对于一般情况,有一定的性能损失
        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            _merge(arr, l, mid, r);
        }
    }

    // 将arr[l...mid]和arr[mid+1...r]两部分进行归并
    private static void _merge(Comparable[] arr, int l, int mid, int r) {
        Comparable[] tmp = Arrays.copyOfRange(arr, l, r + 1);
        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = tmp[j++ - l];
            } else if (j > r) {
                arr[k] = tmp[i++ - l];
            } else if (tmp[i - l].compareTo(tmp[j - l]) < 0) {
                arr[k] = tmp[i++ - l];
            } else {
                arr[k] = tmp[j++ - l];
            }
        }
    }

    public static void main(String[] args) {

        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithms.sort.merge.sort.MergeSort2", arr);

    }

}
