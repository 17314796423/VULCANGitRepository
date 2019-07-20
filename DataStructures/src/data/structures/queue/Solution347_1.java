package data.structures.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Solution347_1 {

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

        PriorityQueue<Freq<Integer>> queue = new PriorityQueue<>();
        for (Integer e : treeMap.keySet()) {
            if(queue.getSize() < k)
                queue.enqueue(new Freq<>(e, treeMap.get(e)));
            else if(queue.getFront().freq < treeMap.get(e)) {
                queue.dequeue();
                queue.enqueue(new Freq<>(e, treeMap.get(e)));
            }
        }

        List res = new ArrayList();
        for (int i = 0; i < k; i++)
            res.add(queue.dequeue().e);
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
        printList((new Solution347_1()).topKFrequent(nums, k));

    }

}
