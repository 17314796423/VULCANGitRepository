package data.structures.queue;

import java.util.*;
import java.util.PriorityQueue;

public class Solution347_3 {

    private class Freq<E>{

        public E e;
        public int freq;

        public Freq(E e, int freq){
            this.e = e;
            this.freq = freq;
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

        PriorityQueue<Freq<Integer>> queue = new PriorityQueue<>(new Comparator<Freq<Integer>>() {
            @Override
            public int compare(Freq<Integer> a, Freq<Integer> b) {
                return a.freq - b.freq;
            }
        });
        for (Integer e : treeMap.keySet()) {
            if(queue.size() < k)
                queue.add(new Freq<>(e, treeMap.get(e)));
            else if(queue.peek().freq < treeMap.get(e)) {
                queue.remove();
                queue.add(new Freq<>(e, treeMap.get(e)));
            }
        }

        List res = new ArrayList();
        for (int i = 0; i < k; i++)
            res.add(queue.remove().e);
        return res;

    }

    private static void printList(List<Integer> nums){
        for(Integer num: nums)
            System.out.print(num + " ");
        System.out.println();
    }

    public static void main(String[] args) {

        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        printList((new Solution347_3()).topKFrequent(nums, k));

    }

}
