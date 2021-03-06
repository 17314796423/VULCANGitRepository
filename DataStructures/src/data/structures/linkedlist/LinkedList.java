package data.structures.linkedlist;

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

    private Node head;
    private int size;

    public int getSize(){
        return  size;
    }

    public LinkedList(){
        head = null;
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
        if(index == 0) {
            head = new Node(e, head);
        }
        else{
            Node prev = head;
            for(int i = 0; i < index - 1; i ++){
                prev = prev.next;
            }
            prev.next = new Node(e, prev.next);
        }
        size ++;
    }

    public void addLast(E e) {
        add(size, e);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("LinkedList: [");
        Node cur = head;
        for(int i=0;i<size;i++) {
            sb.append(cur + "->");
            cur = cur.next;
        }
        sb.append("NULL]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < 5; i ++) {
            list.add(i,i + "");
            System.out.println(list);
        }
        list.addFirst("first");
        System.out.println(list);
        list.addLast("last");
        System.out.println(list);
    }

}
