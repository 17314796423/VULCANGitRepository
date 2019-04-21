package linkedlist.dummy;

public class LinkedList<E> {

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

    private Node dummyHead;
    private int size;

    public int getSize(){
        return  size;
    }

    public LinkedList(){
        dummyHead = new Node();
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void addFirst(E e){
        add(0,e);
    }

    public void add(int index, E e){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        Node prev = dummyHead;
        for(int i = 0; i < index; i ++){
            prev = prev.next;
        }
        prev.next = new Node(e, prev.next);
        size ++;
    }

    public void addLast(E e) {
        add(size, e);
    }

    public E removeFirst(){
        return remove(0);
    }

    public E remove(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++)
            prev = prev.next;
        Node ret = prev.next;
        prev.next = ret.next;
        ret.next = null;
        size --;
        return ret.e;
    }

    public E removeLast(){
        return remove(size - 1);
    }

    public E get(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++)
            cur = cur.next;
        return cur.e;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size - 1);
    }

    public void set(int index, E e){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++)
            cur = cur.next;
        cur.e = e;
    }

    public boolean contains(E e){
        /*Node cur = dummyHead.next;
        for (int i = 0; i < size; i++) {
            if(cur.e.equals(e))
                return true;
            cur = cur.next;
        }*/
        for(Node cur = dummyHead.next;cur != null;cur = cur.next){
            if(cur.e.equals(e))
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node cur = dummyHead.next;
        for(int i=0;i<size;i++) {
            sb.append(cur + "->");
            cur = cur.next;
        }
        sb.append("NULL]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for(int i = 0 ; i < 5 ; i ++){
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.removeFirst();
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);
    }

}
