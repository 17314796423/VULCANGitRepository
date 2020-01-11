package algorithms.sort.heap.index;

import algorithms.sort.SortTestHelper;
import data.structures.array.Array;

import java.util.Random;

public class BinaryMinHeapRev<E extends Comparable<E>> {

    private Array<E> data;
    private Array<Integer> indexes;
    private Array<Integer> rev;

    public BinaryMinHeapRev(int capacity){
        this.data = new Array<>(capacity);
        this.indexes = new Array<>(capacity);
        this.rev = new Array<>(capacity);
    }

    public BinaryMinHeapRev(){
        this.data = new Array<>(10);
        this.indexes = new Array<>(10);
        this.rev = new Array<>(10);
    }

    public BinaryMinHeapRev(E[] arr){
        data = new Array<>(arr.length);
        indexes = new Array<>(arr.length);
        rev = new Array<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            data.addLast(arr[i]);
            indexes.addLast(i);
            rev.addLast(i);
        }
        for(int i = parent(arr.length - 1); i >= 0; i --)
            siftDown(i);
    }

    public int size(){
        return indexes.getSize();
    }

    public boolean isEmpty(){
        return indexes.isEmpty();
    }

    public int parent(int index){
        if(index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return (index - 1) / 2;
    }

    public int leftChild(int index){
        return 2 * index + 1;
    }

    public int rightChild(int index){
        return 2 * index + 2;
    }

    public void add(E e){
        data.addLast(e);
        indexes.addLast(data.getSize() - 1);
        rev.addLast(indexes.getSize() - 1);
        siftUp(indexes.getSize() - 1);
    }

    public void add(int index, E e){
        data.set(index, e);
        indexes.addLast(index);
        rev.set(index, indexes.getSize() - 1);
        siftUp(indexes.getSize() - 1);
    }

    private void siftUp(int index) {
        for(;index > 0 && data.get(indexes.get(index)).compareTo(data.get(indexes.get(parent(index)))) < 0;){
            rev.set(indexes.get(index), parent(index));
            rev.set(indexes.get(parent(index)), index);
            indexes.swap(index, parent(index));
            index = parent(index);
        }
    }

    public E findMin(){
        if(indexes.isEmpty())
            throw new IllegalArgumentException("Can not findMax when data.structures.heap is empty.");
        return data.get(indexes.get(0));
    }

    public E extractMin(){
        E ret = findMin();
        indexes.set(0, indexes.getLast());
        rev.set(indexes.get(0), 0);
        indexes.removeLast();
        //是否花费O(n)的复杂度去清理内存
        siftDown(0);
        return ret;
    }

    public int extractMinIndex(){
        int ret = indexes.get(0);
        indexes.set(0, indexes.getLast());
        rev.set(indexes.get(0), 0);
        indexes.removeLast();
        //是否花费O(n)的复杂度去清理内存
        siftDown(0);
        return ret;
    }

    public E getItem(int index) {
        return data.get(index);
    }

    public void change(int index, E newItem) {
        if (!checkIndex(index))
            throw new IllegalArgumentException("index is illegal!!!");
        int _i = rev.get(index);
        data.set(index, newItem);
        siftUp(_i);
        siftDown(_i);
        return;
    }

    private void siftDown(int index) {
        for(;leftChild(index) < indexes.getSize();){
            int dest = leftChild(index);
            if(dest + 1 < indexes.getSize()
                    && data.get(indexes.get(dest + 1)).compareTo(data.get(indexes.get(dest))) < 0)
                dest ++;
            if(data.get(indexes.get(index)).compareTo(data.get(indexes.get(dest))) <= 0)
                break;
            rev.set(indexes.get(index), dest);
            rev.set(indexes.get(dest), index);
            indexes.swap(index, dest);
            index = dest;
        }
    }

    public E replace(E e){
        E ret = findMin();
        data.addLast(e);
        indexes.addLast(data.getSize() - 1);
        rev.addLast(indexes.getSize() - 1);
        siftDown(0);
        return ret;
    }

    public static void sort(Comparable[] arr) {
//        BinaryMinHeapRev<? extends Comparable> heap = new BinaryMinHeapRev(arr);
//        for (int i = 0; i < arr.length; i++)
//            arr[i] = heap.extractMin();
        //测试change
        BinaryMinHeapRev<Integer> heap = new BinaryMinHeapRev(arr);
        int _i = 0;
        for (int i = 0; i < 1000000; i++)
            while (heap.checkIndex(_i = new Random().nextInt(heap.size()))) {
                heap.change(_i, new Random().nextInt(1000000));
                break;
            }
        for (int i = 0; i < arr.length; i++)
            arr[i] = heap.extractMin();
    }

    private boolean checkIndex(int _i) {
        Integer __i = rev.get(_i);
        if (__i < 0 || __i >= indexes.getSize() && data.get(indexes.get(__i)) == data.get(_i))
            return false;
        return true;
    }

    public static void main(String[] args) {
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, N);
        SortTestHelper.testSort("algorithms.sort.heap.index.BinaryMinHeapRev", arr);
    }

}
