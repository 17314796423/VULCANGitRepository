package map;

import set.FileOperation;

import java.util.ArrayList;

public class LinkedListMap<K,V> implements Map<K,V>{

    private class Node{
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, Node next){
            this(key, null, next);
        }

        public Node(){
            this(null, null, null);
        }

        @Override
        public String toString(){
            return  key.toString() + ":" + value.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap(){
        dummyHead = new Node();
        size = 0;
    }

    private Node getNode(K key){
        Node prev = dummyHead;
        for(;prev.next != null;prev = prev.next){
            if(key.equals(prev.next.key))
                return prev.next;
        }
        return null;
    }

    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node != null)
            node.value = value;
        else{
            dummyHead.next = new Node(key, value, dummyHead.next);
            size ++;
        }
    }

    @Override
    public V remove(K key) {
        Node prev = dummyHead;
        for (;prev.next != null;prev = prev.next){
            if(key.equals(prev.next.key))
                break;
        }
        if (prev != null) {
            Node ret = prev.next;
            prev.next = ret.next;
            ret.next = null;
            size --;
            return ret.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node != null?node.value:null;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(key);
        if (node != null)
            node.value = newValue;
        else
            throw new IllegalArgumentException(key + " doesn't exist!");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            Map<String, Integer> map = new LinkedListMap<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }

}
