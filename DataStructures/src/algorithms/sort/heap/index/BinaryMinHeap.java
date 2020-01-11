package algorithms.sort.heap.index;

import algorithms.sort.SortTestHelper;
import data.structures.array.Array;

import java.util.Random;

public class BinaryMinHeap<E extends Comparable<E>> {

    private E[] data;
    private Array<Integer> indexes;
    private Integer[] rev;

    public BinaryMinHeap(int capacity){
        this.data = (E[]) new Comparable[capacity];
        this.indexes = new Array<>(capacity);
        this.rev = new Integer[capacity];
    }

    public BinaryMinHeap(){
        this.data = (E[]) new Comparable[10];
        this.indexes = new Array<>(10);
        this.rev = new Integer[10];
    }

    public BinaryMinHeap(E[] arr){
        this.data = (E[]) new Comparable[arr.length];
        this.indexes = new Array<>(arr.length);
        this.rev = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
            indexes.addLast(i);
            rev[i] = i;
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

    public void add(int index, E e){
        data[index] = e;
        indexes.addLast(index);
        rev[index] = indexes.getSize() - 1;
        siftUp(indexes.getSize() - 1);
    }

    private void siftUp(int index) {
        for(;index > 0 && data[indexes.get(index)].compareTo(data[indexes.get(parent(index))]) < 0;){
            rev[indexes.get(index)] = parent(index);
            rev[indexes.get(parent(index))] = index;
            indexes.swap(index, parent(index));
            index = parent(index);
        }
    }

    public E findMin(){
        if(indexes.isEmpty())
            throw new IllegalArgumentException("Can not findMax when data.structures.heap is empty.");
        return data[indexes.get(0)];
    }

    public E extractMin(){
        E ret = findMin();
        indexes.set(0, indexes.getLast());
        rev[indexes.get(0)] = 0;
        indexes.removeLast();
        //是否花费O(n)的复杂度去清理内存
        siftDown(0);
        return ret;
    }

    public int extractMinIndex(){
        int ret = indexes.get(0);
        indexes.set(0, indexes.getLast());
        rev[indexes.get(0)] = 0;
        indexes.removeLast();
        //是否花费O(n)的复杂度去清理内存
        siftDown(0);
        return ret;
    }

    public E getItem(int index) {
        return data[index];
    }

    public void change(int index, E newItem) {
        if (!checkIndex(index))
            throw new IllegalArgumentException("index is illegal!!!");
        int _i = rev[index];
        data[index] = newItem;
        siftUp(_i);
        siftDown(_i);
        return;
    }

    private void siftDown(int index) {
        for(;leftChild(index) < indexes.getSize();){
            int dest = leftChild(index);
            if(dest + 1 < indexes.getSize()
                    && data[indexes.get(dest + 1)].compareTo(data[indexes.get(dest)]) < 0)
                dest ++;
            if(data[indexes.get(index)].compareTo(data[indexes.get(dest)]) <= 0)
                break;
            rev[indexes.get(index)] = dest;
            rev[indexes.get(dest)] = index;
            indexes.swap(index, dest);
            index = dest;
        }
    }

    public E replace(Integer index, E e){
        E ret = findMin();
        data[index] = e;
        indexes.addLast(index);
        rev[index] = indexes.getSize() - 1;
        siftDown(0);
        return ret;
    }

    public static void sort(Comparable[] arr) {
//        BinaryMinHeap<? extends Comparable> heap = new BinaryMinHeap(arr);
//        for (int i = 0; i < arr.length; i++)
//            arr[i] = heap.extractMin();
        //测试change
        BinaryMinHeap<Integer> heap = new BinaryMinHeap(arr);
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
        Integer __i = rev[_i];
        if (__i < 0 || __i >= indexes.getSize() && data[indexes.get(__i)] == data[_i])
            return false;
        return true;
    }

    public static void main(String[] args) {
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, N);
        SortTestHelper.testSort("algorithms.sort.heap.index.BinaryMinHeap", arr);
    }

}
