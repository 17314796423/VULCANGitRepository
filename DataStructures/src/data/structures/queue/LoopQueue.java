package data.structures.queue;

public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    private int front,tail;

    public LoopQueue(int capacity){
        data = (E[]) new Object[capacity + 1];
        front = tail = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public int getSize() {
        if(front <= tail)
            return tail - front;
        else
            return tail + data.length - front;
    }

    @Override
    public boolean isEmpty() {
        return tail == front;
    }

    @Override
    public void enqueue(E e) {
        if((tail + 1) % data.length == front)
            resize(2 * getCapacity());
        data[tail] = e;
        tail = (tail + 1) % data.length;
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty data.structures.queue.");
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        if(getSize() <= getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);
        return ret;
    }

    private void resize(int capacity) {
        E[] newData = (E[]) new Object[capacity + 1];
        int size = getSize();
        for(int i = front; i < front + size; i ++){
            newData[i - front] = data[i % data.length];
        }
        data = newData;
        front = 0;
        tail = front + size;
    }

    @Override
    public E getFront() {
        return data[front];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(String.format("LoopQueue: front=%d [", front));
        for (int i = front; i < front + getSize(); i ++) {
            sb.append(data[i % data.length]);
            if(i != front + getSize() -1)
                sb.append(",");
        }
        sb.append(String.format("] tail=%d capacity=%d size=%d", tail, getCapacity(), getSize()));
        return sb.toString();
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new LoopQueue<>();
        for(int i = 0 ; i < 50 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);
            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }

}
