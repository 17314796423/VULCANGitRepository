package algorithms.sort.merge.sort;

import algorithms.sort.SortTestHelper;
import algorithms.sort.insertion.sort.InsertionSort;

import java.util.Arrays;

// 优化的Merge Sort算法
public class GetInversePairs {

    public static int sort(Comparable[] arr) {
        return _mergeSort(arr, 0, arr.length - 1);
    }

    // 递归使用归并排序,对arr[l...r]的范围进行排序
    private static int _mergeSort(Comparable[] arr, int l, int r) {
        // 优化2: 对于小规模数组, 使用插入排序
        if( r - l <= 15 ){
            InsertionSort.sort(arr, l, r);
            return 0;
        }
        int mid = l + (r - l) / 2;
        int t1 = _mergeSort(arr, l, mid);
        int t2 = _mergeSort(arr, mid + 1, r);

        // 优化1: 对于arr[mid] <= arr[mid+1]的情况,不进行merge
        // 对于近乎有序的数组非常有效,但是对于一般情况,有一定的性能损失
        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            return t1 + t2 + _merge(arr, l, mid, r);
        }
        return t1 + t2;
    }

    // 将arr[l...mid]和arr[mid+1...r]两部分进行归并
    private static int _merge(Comparable[] arr, int l, int mid, int r) {
        Comparable[] tmp = Arrays.copyOfRange(arr, l, r + 1);
        int i = l, j = mid + 1, t = 0;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = tmp[j++ - l];
            } else if (j > r) {
                arr[k] = tmp[i++ - l];
            } else if (tmp[i - l].compareTo(tmp[j - l]) < 0) {
                arr[k] = tmp[i++ - l];
            } else {
                if (tmp[i - l].compareTo(tmp[j - l]) > 0) {
                    t += (mid - i + 1);
                }
                arr[k] = tmp[j++ - l];
            }
        }
        return t;
    }

    public static void main(String[] args) {

        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithms.sort.merge.sort.GetInversePairs", arr);

    }

}
