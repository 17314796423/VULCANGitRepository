package algorithms.sort.heap.index;

import algorithms.sort.SortTestHelper;
import data.structures.array.Array;

import java.util.Random;

public class BinaryMaxHeap<E extends Comparable<E>> {

    private Array<E> data;
    private Array<Integer> indexes;

    public BinaryMaxHeap(int capacity){
        this.data = new Array<>(capacity);
        this.indexes = new Array<>(capacity);
    }

    public BinaryMaxHeap(){
        this.data = new Array<>(10);
        this.indexes = new Array<>(10);
    }

    public BinaryMaxHeap(E[] arr){
        data = new Array<>(arr.length);
        indexes = new Array<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            data.addLast(arr[i]);
            indexes.addLast(i);
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
        siftUp(indexes.getSize() - 1);
    }

    private void siftUp(int index) {
        for(;index > 0 && data.get(indexes.get(index)).compareTo(data.get(indexes.get(parent(index)))) > 0;){
            indexes.swap(index, parent(index));
            index = parent(index);
        }
    }

    public E findMax(){
        if(data.isEmpty())
            throw new IllegalArgumentException("Can not findMax when data.structures.heap is empty.");
        return data.get(indexes.get(0));
    }

    public E extractMax(){
        E ret = findMax();
        indexes.set(0, indexes.getLast()); //这样写目的在于最后一个元素的时候会数组越界
        indexes.removeLast();
        siftDown(0);
        return ret;
    }

    public int extractMaxIndex(){
        int ret = indexes.get(0);
        indexes.set(0, indexes.getLast()); //这样写目的在于最后一个元素的时候会数组越界
        indexes.removeLast();
        siftDown(0);
        return ret;
    }

    public E getItem(int index) {
        return data.get(index);
    }

    public void change(int index, E newItem) {
        data.set(index, newItem);
        for (int i = 0; i < indexes.getSize(); i++) {
            if (indexes.get(i) == index) {
                siftUp(i);
                siftDown(i);
                return;
            }
        }
    }

    private void siftDown(int index) {
        for(;leftChild(index) < indexes.getSize();){
            int dest = leftChild(index);
            if(dest + 1 < indexes.getSize()
                    && data.get(indexes.get(dest + 1)).compareTo(data.get(indexes.get(dest))) > 0)
                dest ++;
            if(data.get(indexes.get(index)).compareTo(data.get(indexes.get(dest))) >= 0)
                break;
            indexes.swap(index, dest);
            index = dest;
        }
    }

    public E replace(E e){
        E ret = findMax();
        data.addLast(e);
        indexes.set(0, data.getSize() - 1);
        siftDown(0);
        return ret;
    }

    public static void sort(Comparable[] arr) {
//        BinaryMaxHeap<? extends Comparable> heap = new BinaryMaxHeap(arr);
//        for (int i = arr.length - 1; i >= 0; i--)
//            arr[i] = heap.extractMax();
        //测试change
        BinaryMaxHeap<Integer> heap = new BinaryMaxHeap(arr);
        for (int i = 0; i < 1000000; i++)
            heap.change(new Random().nextInt(heap.size()), new Random().nextInt(1000000));
        for (int i = arr.length - 1; i >= 0; i--)
            arr[i] = heap.extractMax();
    }

    public static void main(String[] args) {
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, N);
        SortTestHelper.testSort("algorithms.sort.heap.index.BinaryMaxHeap", arr);
    }

}
