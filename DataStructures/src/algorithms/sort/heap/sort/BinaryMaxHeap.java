package algorithms.sort.heap.sort;

import data.structures.array.Array;

public class BinaryMaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public BinaryMaxHeap(int capacity){
        this.data = new Array<>(capacity);
    }

    public BinaryMaxHeap(){
        this.data = new Array<>(10);
    }

    public BinaryMaxHeap(E[] arr){
        data = new Array<>(arr);
        for(int i = parent(arr.length - 1); i >= 0; i --)
            siftDown(i);
    }

    private void _sort(E[] arr){
        data = new Array<E>(arr, false);
        for(int i = parent(arr.length - 1); i >= 0; i --)
            siftDown(i);
        for (int i = data.getSize() - 1; i > 0; i--) {
            data.swap(0, i);
            _siftDown(0, i);
        }
    }

    public int size(){
        return data.getSize();
    }

    public boolean isEmpty(){
        return data.isEmpty();
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
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int index) {
        for(;index > 0 && data.get(index).compareTo(data.get(parent(index))) > 0;){
            data.swap(index, parent(index));
            index = parent(index);
        }
    }

    public E findMax(){
        if(data.isEmpty())
            throw new IllegalArgumentException("Can not findMax when data.structures.heap is empty.");
        return data.get(0);
    }

    public E extractMax(){
        E ret = findMax();
        data.set(0,data.getLast());
        data.removeLast();
        siftDown(0);
        return ret;
    }

    private void siftDown(int index) {
        for(;leftChild(index) < data.getSize();){
            int dest = leftChild(index);
            if(dest + 1 < data.getSize()
                    && data.get(dest + 1).compareTo(data.get(dest)) > 0)
                dest ++;
            if(data.get(index).compareTo(data.get(dest)) >= 0)
                break;
            data.swap(index, dest);
            index = dest;
        }
    }

    private void _siftDown(int index, int n) {
        for(;leftChild(index) < n;){
            int dest = leftChild(index);
            if(dest + 1 < n
                    && data.get(dest + 1).compareTo(data.get(dest)) > 0)
                dest ++;
            if(data.get(index).compareTo(data.get(dest)) >= 0)
                break;
            data.swap(index, dest);
            index = dest;
        }
    }

    public E replace(E e){
        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }


    public static void sort(Comparable[] arr) {
        new BinaryMaxHeap()._sort(arr);
    }

}
