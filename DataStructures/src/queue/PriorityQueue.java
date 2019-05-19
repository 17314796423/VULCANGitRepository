package queue;

import heap.BinaryMaxHeap;

public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private BinaryMaxHeap<E> binaryMaxHeap;

    public PriorityQueue(){
        binaryMaxHeap = new BinaryMaxHeap<>();
    }

    public PriorityQueue(int capacity){
        binaryMaxHeap = new BinaryMaxHeap<>(capacity);
    }

    @Override
    public int getSize() {
        return binaryMaxHeap.size();
    }

    @Override
    public boolean isEmpty() {
        return binaryMaxHeap.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        binaryMaxHeap.add(e);
    }

    @Override
    public E dequeue() {
        return binaryMaxHeap.extractMax();
    }

    @Override
    public E getFront() {
        return binaryMaxHeap.findMax();
    }

}
