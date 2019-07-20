package data.structures.queue;

import java.util.*;
import java.util.PriorityQueue;

public class Solution347_4 {

    public List<Integer> topKFrequent(int[] nums, int k) {

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int num : nums) {
            if(treeMap.containsKey(num))
                treeMap.put(num, treeMap.get(num) + 1);
            else
                treeMap.put(num, 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(
                Comparator.comparingInt(treeMap::get)
        );
        for (Integer e : treeMap.keySet()) {
            if(queue.size() < k)
                queue.add(e);
            else if(treeMap.get(queue.peek()) < treeMap.get(e)) {
                queue.remove();
                queue.add(e);
            }
        }

        List res = new ArrayList();
        for (int i = 0; i < k; i++)
            res.add(queue.remove());
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
        printList((new Solution347_4()).topKFrequent(nums, k));

    }

}
