package algorithms.sort.selection.sort;

import algorithms.sort.SortTestHelper;

public class SelectionSort {

    // 我们的算法类不允许产生任何实例
    private SelectionSort(){}

    public static void sort(Comparable[] arr){
        int minIndex;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if(arr[j].compareTo(arr[minIndex]) < 0){
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    private static void swap(Comparable[] arr, int i, int j) {
        Comparable t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {

        // 测试Integer
        Integer[] arr = {10,9,8,7,6,5,4,3,2,1};
        SelectionSort.sort(arr);
        for( int i = 0 ; i < arr.length ; i ++ ){
            System.out.print(arr[i]);
            System.out.print(' ');
        }
        System.out.println();

        // 测试Double
        Double[] b = {4.4, 3.3, 2.2, 1.1};
        SelectionSort.sort(b);
        for( int i = 0 ; i < b.length ; i ++ ){
            System.out.print(b[i]);
            System.out.print(' ');
        }
        System.out.println();

        // 测试String
        String[] c = {"D", "C", "B", "A"};
        SelectionSort.sort(c);
        for( int i = 0 ; i < c.length ; i ++ ){
            System.out.print(c[i]);
            System.out.print(' ');
        }
        System.out.println();

        // 测试自定义的类 Student
        Student[] d = new Student[4];
        d[0] = new Student("D",90);
        d[1] = new Student("C",100);
        d[2] = new Student("B",95);
        d[3] = new Student("A",95);
        SelectionSort.sort(d);
        for( int i = 0 ; i < d.length ; i ++ )
            System.out.println(d[i]);

        int N = 20000;
        Integer[] arr1 = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithms.sort.selection.sort.SelectionSort", arr1);

    }

}