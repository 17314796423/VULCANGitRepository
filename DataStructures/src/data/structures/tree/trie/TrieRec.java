package data.structures.tree.trie;

import java.util.Map;
import java.util.TreeMap;

public class TrieRec {

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
    private int size;

    public TrieRec() {
        this.root = new Node(true);
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(String word) {
        add(root, new StringBuilder(word));
    }

    private void add(Node node, StringBuilder word) {
        if (word.length() == 0) {
            node.isWord = true;
            size++;
            return;
        }
        char c = word.charAt(0);
        if (node.next.get(c) == null)
            node.next.put(c, new Node());
        add(node.next.get(c), word.deleteCharAt(0));
    }

    public boolean contains(String word) {
        return contains(root, new StringBuilder(word));
    }

    private boolean contains(Node node, StringBuilder word) {
        if (word.length() == 0)
            return node.isWord;
        char c = word.charAt(0);
        if (node.next.get(c) == null)
            return false;
        return contains(node.next.get(c), word.deleteCharAt(0));
    }

    public boolean remove(String word) {
        return remove(root, new StringBuilder(word), new boolean[]{false});
    }

    /**
     *
     * @return 为null表示需要父节点删除当前节点
     */
    private Boolean remove(Node node, StringBuilder word, boolean[] flag) {
        if (word.length() == 0) {
            if(node.isWord) {
                size--;
                if (node.next.size() > 0) {
                    node.isWord = false;
                    return true;
                } else {
                    return null;
                }
            }
            return false;
        }
        char c = word.charAt(0);
        if (node.next.get(c) == null) {
            return false;
        }
        Boolean result = remove(node.next.get(c), word.deleteCharAt(0), flag);
        if(result == null){
            node.next.remove(c);
            //该条件成立则表明该节点开始向上不需要被删除
            if(node.isWord || node.next.size() > 0) {
                return true;
            }
            //表明需要上层调用删除自己
            return null;
        }
        else {
            return result;
        }
    }

    public boolean isPrefix(String prefix) {
        return isPrefix(root, new StringBuilder(prefix));
    }

    private boolean isPrefix(Node node, StringBuilder prefix) {
        if (prefix.length() == 0)
            return true;
        char c = prefix.charAt(0);
        if (node.next.get(c) == null)
            return false;
        return isPrefix(node.next.get(c), prefix.deleteCharAt(0));
    }

    public boolean match(String word) {
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

}
