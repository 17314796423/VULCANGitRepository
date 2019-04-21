package linkedlist.dummy.doublecircular;

public class LinkedList<E> {

    private class Node {

        public E e;
        public Node next;
        public Node prev;

        public Node(E e, Node next, Node prev){
            this.e = e;
            this.next = next;
            this.prev = prev;
        }

        public Node(E e){
            this(e,null,null);
        }

        public Node(){
            this(null,null,null);
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
        dummyHead.next = dummyHead;
        dummyHead.prev = dummyHead;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void doAdd(int index,E e) {
        Node prev = dummyHead;
        if(isPrev(index)) {
            for(int i = 0; i < index; i ++){
                prev = prev.next;
            }
            prev.next = new Node(e, prev.next, prev);
            prev.next.next.prev = prev.next;
        } else {
            for(int i = 0; i < size - index; i ++){
                prev = prev.prev;
            }
            prev.prev = new Node(e, prev, prev.prev);
            prev.prev.prev.next = prev.prev;
        }
        size ++;
    }

    public void addFirst(E e){
        add(0,e);
    }

    public void add(int index, E e){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        doAdd(index, e);
    }

    public void addLast(E e) {
        add(size, e);
    }

    private E doRemove(int index){
        Node prev = dummyHead;
        Node ret;
        if(isPrev(index)){
            for(int i = 0; i < index; i ++){
                prev = prev.next;
            }
            ret = prev.next;
            prev.next = ret.next;
            prev.next.prev = prev;
        } else {
            for (int i = 0; i < size - (index + 1); i++) {
                prev = prev.prev;
            }
            ret = prev.prev;
            prev.prev = ret.prev;
            prev.prev.next = prev;
        }
        ret.next = null;
        ret.prev = null;
        size --;
        return ret.e;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E remove(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        return doRemove(index);
    }

    public E removeLast(){
        return remove(size - 1);
    }

    public E get(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        return getCur(index).e;
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
        getCur(index).e = e;
    }

    public void setFirst(E e) {
        set(0, e);
    }

    public void setLast(E e) {
        set(size - 1, e);
    }

    private Node getCur(int index) {
        Node cur;
        if(isPrev(index)) {
            cur = dummyHead.next;
            for (int i = 0; i < index; i++)
                cur = cur.next;
        } else {
            cur = dummyHead.prev;
            for (int i = 0; i < size - (index + 1); i++)
                cur = cur.prev;
        }
        return cur;
    }

    private boolean isPrev(int index) {
        return index < size/2;
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
        for (; !cur.equals(dummyHead); ) {
            sb.append(cur + "->");
            cur = cur.next;
        }
        sb.append("NULL]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for(int i = 0 ; i < 10 ; i ++){
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.add(9,666);
        System.out.println(linkedList);

        linkedList.remove(9);
        System.out.println(linkedList);

        linkedList.add(7,777);
        System.out.println(linkedList.get(7));
        System.out.println(linkedList);
        linkedList.set(7,888);
        System.out.println(linkedList.get(7));
        System.out.println(linkedList);
    }

}
