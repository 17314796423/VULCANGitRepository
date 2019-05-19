package heap;

import queue.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class Solution347 {

    private class Freq<E> implements Comparable<Freq<E>>{

        public E e;
        public int freq;

        public Freq(E e, int freq){
            this.e = e;
            this.freq = freq;
        }

        @Override
        public int compareTo(Freq another){
            if(this.freq < another.freq)
                return 1;
            else if(this.freq > another.freq)
                return -1;
            else
                return 0;
        }

    }

    public List<Integer> topKFrequent(int[] nums, int k) {

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int num : nums) {
            if(treeMap.containsKey(num))
                treeMap.put(num, treeMap.get(num) + 1);
            else
                treeMap.put(num, 1);
        }

        BinaryMaxHeap<Freq<Integer>> maxHeap = new BinaryMaxHeap<>();
        for (Integer e : treeMap.keySet()) {
            if(maxHeap.size() < k)
                maxHeap.add(new Freq<>(e, treeMap.get(e)));
            else if(maxHeap.findMax().freq < treeMap.get(e))
                maxHeap.replace(new Freq<>(e, treeMap.get(e)));
        }

        List res = new ArrayList();
        for (int i = 0; i < k; i++)
            res.add(maxHeap.extractMax().e);
        return res;

    }

    private static void printList(List<Integer> nums){
        for(Integer num: nums)
            System.out.print(num + " ");
        System.out.println();
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        int n = 10000000;
        int[] nums = new int[n];
        int k = 20;
        Random random = new Random();
        for (int i = 0; i < n; i++)
            nums[i] = new Random().nextInt(Integer.MAX_VALUE);
        printList((new Solution347()).topKFrequent(nums, k));
        long end = System.currentTimeMillis();
        System.out.println("this program cost " + (end - start) / 1000.0 + "s to complete calculation");

    }

}
