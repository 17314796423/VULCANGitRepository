package algorithms.sort.merge.sort;

import algorithms.sort.SortTestHelper;

import java.util.Arrays;

public class MergeSort {

    public static void sort(Comparable[] arr) {
        _mergeSort(arr, 0, arr.length - 1);
    }

    // 递归使用归并排序,对arr[l...r]的范围进行排序
    private static void _mergeSort(Comparable[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = l + (r - l) / 2;
        _mergeSort(arr, l, mid);
        _mergeSort(arr, mid + 1, r);
        _merge(arr, l, mid, r);
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

        // Merge Sort是我们学习的第一个O(nlogn)复杂度的算法
        // 可以在1秒之内轻松处理100万数量级的数据
        // 注意：不要轻易尝试使用SelectionSort, InsertionSort或者BubbleSort处理100万级的数据
        // 否则，你就见识了O(n^2)的算法和O(nlogn)算法的本质差异：）
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithms.sort.merge.sort.MergeSort", arr);

    }
}