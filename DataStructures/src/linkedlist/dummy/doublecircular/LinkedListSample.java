package linkedlist.dummy.doublecircular;

public class LinkedListSample<E> {

    private class Node {

        public E e;
        public Node next;
        public Node prev;

        public Node(E e, Node next, Node prev) {
            this.e = e;
            this.next = next;
            this.prev = prev;
        }

        public Node(E e) {
            this(e, null, null);
        }

        public Node() {
            this(null, null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }

    }

    private Node dummyHead;
    private int size;

    public int getSize() {
        return size;
    }

    public LinkedListSample() {
        dummyHead = new Node();
        dummyHead.next = dummyHead;
        dummyHead.prev = dummyHead;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        prev.next = new Node(e, prev.next, prev);
        prev.next.next.prev = prev.next;
        size++;
    }

    public void addReverse(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.prev;
        }
        prev.prev = new Node(e, prev, prev.prev);
        prev.prev.prev.next = prev.prev;
        size++;
    }

    public void addLast(E e) {
        addReverse(0, e);
    }

    public E removeFirst() {
        return remove(0);
    }

    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node ret = prev.next;
        prev.next = ret.next;
        prev.next.prev = prev;
        ret.next = null;
        ret.prev = null;
        size--;
        return ret.e;
    }

    public E removeReverse(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index.");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.prev;
        }
        Node ret = prev.prev;
        prev.prev = ret.prev;
        prev.prev.next = prev;
        ret.prev = null;
        ret.next = null;
        size--;
        return ret.e;
    }

    public E removeLast() {
        return removeReverse(0);
    }

    public void removeElement(E e){
        Node prev = dummyHead;
        for(;prev.next !=  null;prev = prev.next){
            if(prev.next.e.equals(e)){
                Node ret = prev.next;
                prev.next = ret.next;
                ret.next = null;
                size --;
                return;
            }
        }
    }

    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        return getCur(index).e;
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    public void set(int index, E e) {
        if (index < 0 || index >= size)
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
        Node cur = null;
        if (index + 1 <= size / 2) {
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

    public boolean contains(E e) {
        /*Node cur = dummyHead.next;
        for (int i = 0; i < size; i++) {
            if(cur.e.equals(e))
                return true;
            cur = cur.next;
        }*/
        for (Node cur = dummyHead.next; cur != null; cur = cur.next) {
            if (cur.e.equals(e))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
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
        LinkedListSample<Integer> linkedList = new LinkedListSample<>();
        for (int i = 0; i < 10; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.add(8, 666);
        System.out.println(linkedList);

        linkedList.remove(8);
        System.out.println(linkedList);

        linkedList.removeFirst();
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);

        linkedList.add(7, 777);
        System.out.println(linkedList.get(7));
        System.out.println(linkedList);

        linkedList.set(7, 888);
        System.out.println(linkedList.get(7));
        System.out.println(linkedList);

        linkedList.removeElement(888);
        System.out.println(linkedList);
    }

}

