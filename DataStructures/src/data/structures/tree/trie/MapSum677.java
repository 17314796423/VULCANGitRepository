package data.structures.tree.trie;

import java.util.Map;
import java.util.TreeMap;

class MapSum677 {

    private class Node {

        Map<Character, Node> next;
        int value;

        public Node(int value) {
            this.next = new TreeMap<>();
            this.value = value;
        }

        public Node() {
            this(0);
        }

    }

    private Node root;

    /** Initialize your data structure here. */
    public MapSum677() {
        this.root = new Node();
    }
    
    public void insert(String key, int val) {
        Node cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }
        cur.value = val;
    }
    
    public int sum(String prefix) {
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null)
                return 0;
            cur = cur.next.get(c);
        }
        return sum(cur);
    }

    private int sum(Node node){
        int res = node.value;
        for (char c : node.next.keySet()) {
            res += sum(node.next.get(c));
        }
        return res;
    }

}