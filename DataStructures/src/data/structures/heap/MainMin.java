package data.structures.heap;

import java.util.Random;

public class MainMin {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        int n = 1000000;
        BinaryMinHeap<Integer> heap = new BinaryMinHeap<>();
        Random random = new Random();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
            heap.add(random.nextInt(Integer.MAX_VALUE));

        for (int i = 0; i < n; i++)
            arr[i] = heap.extractMin();

        System.out.println("length : " + arr.length);

        for (int i = 0; i < n - 1; i++) {
            if(arr[i] > arr[i + 1])
                throw new RuntimeException(">>>>>>>>>>>>>>>>>>>>>>Error<<<<<<<<<<<<<<<<<<<<<<");
        }

        System.out.println("Complete!");

        long end = System.currentTimeMillis();

        System.out.println("this program cost " + (end - start) / 1000.0 + "s to complete calculation");

    }

}
