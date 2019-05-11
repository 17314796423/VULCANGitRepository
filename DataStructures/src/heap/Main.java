package heap;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        int n = 100000000;
        BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
        Random random = new Random();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
            heap.add(random.nextInt(Integer.MAX_VALUE));

        for (int i = 0; i < n; i++)
            arr[i] = heap.extractMax();

        System.out.println("length : " + arr.length);

        for (int i = 0; i < n - 1; i++) {
            if(arr[i] < arr[i + 1])
                throw new RuntimeException(">>>>>>>>>>>>>>>>>>>>>>Error<<<<<<<<<<<<<<<<<<<<<<");
        }

        System.out.println("Complete!");

        long end = System.currentTimeMillis();

        System.out.println("this program cost " + (end - start) / 1000.0 + "s to complete calculation");

    }

}
