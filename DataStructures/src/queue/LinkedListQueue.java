package queue;

public class LinkedListQueue<E> implements Queue<E> {

    private class Node {

        public E e;
        public Node next;

        public Node(E e, Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }

        @Override
        public String toString(){
            return  e.toString();
        }

    }

    private int size;
    private Node head;
    private Node tail;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E e) {
        Node node = new Node(e);
        if(tail == null){
            head = tail = node;
        } else {
            tail.next = node;
            tail = tail.next;
        }
        size ++;
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        Node ret = head;
        head = ret.next;
        ret.next = null;
        if(head == null)
            tail = null;
        size --;
        return ret.e;
    }

    @Override
    public E getFront() {
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty.");
        return head.e;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Queue: front [");
        for(Node cur = head;cur != null;cur = cur.next)
            sb.append(cur + "->");
        sb.append("NULL] tail");
        return sb.toString();
    }

    public static void main(String[] args){

        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        for(int i = 0 ; i < 20 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }

}
