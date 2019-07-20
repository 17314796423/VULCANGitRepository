package data.structures.tree.trie;

import data.structures.set.FileOperation;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WordDictionary211 {

    private class LinkedList<E> {

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

        public LinkedList() {
            dummyHead = new Node();
            dummyHead.next = dummyHead;
            dummyHead.prev = dummyHead;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void doAdd(int index, E e) {
            Node prev = dummyHead;
            if (isPrev(index)) {
                for (int i = 0; i < index; i++) {
                    prev = prev.next;
                }
                prev.next = new Node(e, prev.next, prev);
                prev.next.next.prev = prev.next;
            } else {
                for (int i = 0; i < size - index; i++) {
                    prev = prev.prev;
                }
                prev.prev = new Node(e, prev, prev.prev);
                prev.prev.prev.next = prev.prev;
            }
            size++;
        }

        public void addFirst(E e) {
            add(0, e);
        }

        public void add(int index, E e) {
            if (index < 0 || index > size)
                throw new IllegalArgumentException("Add failed. Illegal index.");
            doAdd(index, e);
        }

        public void addLast(E e) {
            add(size, e);
        }

        private E doRemove(int index) {
            Node prev = dummyHead;
            Node ret;
            if (isPrev(index)) {
                for (int i = 0; i < index; i++) {
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
            size--;
            return ret.e;
        }

        public E removeFirst() {
            return remove(0);
        }

        public E remove(int index) {
            if (index < 0 || index >= size)
                throw new IllegalArgumentException("Remove failed. Illegal index.");
            return doRemove(index);
        }

        public E removeLast() {
            return remove(size - 1);
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
            Node cur;
            if (isPrev(index)) {
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
            return index < size / 2;
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

    }

    private interface Queue<E>{

        int getSize();
        boolean isEmpty();
        void enqueue(E e);
        E dequeue();
        E getFront();

    }

    private class LoopQueue<E> implements Queue<E> {

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

    }

    private class Node {

        Map<Character, Node> next;
        boolean isWord;

        public Node(boolean isWord) {
            this.next = new TreeMap<>();
            this.isWord = isWord;
        }

        public Node() {
            this(false);
        }

    }

    private Node root;

    public WordDictionary211() {
        this.root = new Node();
    }

    public void addWord(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }
        cur.isWord = true;
    }

    public boolean search(String word){
        /*Queue<LinkedList> data.structures.queue = new LoopQueue<>();
        Node cur = root;
        boolean flag = true;
        char c;
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            if(!data.structures.queue.isEmpty() && flag){
                cur = (Node) data.structures.queue.dequeue().getFirst();
                if(cur.next.get(c) == null && !data.structures.queue.isEmpty()){
                    i --;
                    continue;
                }
                else if(cur.next.get(c) == null && data.structures.queue.isEmpty())
                    return false;
                else {
                    flag = false;
                    cur = cur.next.get(c);
                }
            }else {
                if (c != '.') {
                    if (cur.next.get(c) == null) {
                        if(flag)
                            return false;
                        else{
                            flag = true;
                            i = (int) data.structures.queue.getFront().getLast();
                            continue;
                        }
                    }
                    cur = cur.next.get(c);
                } else {
                    for (Character ch : cur.next.keySet()) {
                        //if (cur.next.get(ch) != null)
                        LinkedList<Object> list = new LinkedList<>();
                        list.addFirst(cur.next.get(ch));
                        list.addLast(i);
                        data.structures.queue.enqueue(list);
                    }
                }
            }
        }
        return cur.isWord;*/
        return match(root, word);
    }

    private boolean match(Node node, String word) {
        if (word.length() == 0)
            return node.isWord;
        char c = word.charAt(0);
        if (c != '.') {
            if (node.next.get(c) == null)
                return false;
            return match(node.next.get(c), word.substring(1));
        } else {
            for (Character key : node.next.keySet()) {
                if (match(node.next.get(key), word.substring(1)))
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList();
        WordDictionary211 wordDictionary = new WordDictionary211();
        FileOperation.readFile("The-Decameron-Giovanni-Boccaccio.txt", list);
        for (String s : list) {
            wordDictionary.addWord(s);
        }
        System.out.println(wordDictionary.search("a..ument"));
        /*Trie trieRec = new Trie();//remove测试用力
        trieRec.put("goooooood");
        trieRec.put("go");
        System.out.println(trieRec.size());
        System.out.println(trieRec.remove("goooooood"));
        System.out.println(trieRec.size());
        System.out.println(trieRec.contains("goooooood"));
        System.out.println(trieRec.contains("go"));
        System.out.println(trieRec.isPrefix("goo"));*/
    }

}

