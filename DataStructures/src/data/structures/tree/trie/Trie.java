package data.structures.tree.trie;

import data.structures.linkedlist.dummy.doublecircular.LinkedList;
import data.structures.queue.LoopQueue;
import data.structures.queue.Queue;
import data.structures.stack.ArrayStack;
import data.structures.stack.Stack;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author VULCAN
 */
public class Trie {

    private class Node {

        Map<Character, Node> next;
        boolean isWord;

        private Node(boolean isWord) {
            this.next = new TreeMap<>();
            this.isWord = isWord;
        }

        private Node() {
            this(false);
        }

    }

    private Node root;
    private int size;

    public Trie() {
        this.root = new Node();
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }

    public boolean contains(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    public boolean match(String word) {
        Queue<LinkedList> queue = new LoopQueue<>();
        Node cur = root;
        boolean flag = true;
        char c;
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            if(!queue.isEmpty() && flag){
                cur = (Node) queue.dequeue().getFirst();
                if(cur.next.get(c) == null && !queue.isEmpty()){
                    cur = (Node) queue.dequeue().getFirst();
                    i --;
                }
                else if(cur.next.get(c) == null && queue.isEmpty()) {
                    return false;
                } else {
                    flag = false;
                    cur = cur.next.get(c);
                }
            }else {
                if (c != '.') {
                    if (cur.next.get(c) == null) {
                        if(flag) {
                            return false;
                        }else{
                            flag = true;
                            i = (int) queue.getFront().getLast();
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
                        queue.enqueue(list);
                    }
                }
            }
        }
        return cur.isWord;
    }

    /**
     * 删除指定的单词，然后单词后面没有节点则从该节点开始向上删除不是多个子节点或不是单词的父节点，如果单词后面有节点则不需要删除该节点，直接改isWord为false
     * 这个非递归方式缺陷在于实现并不知道有没有必要压栈，因为如果最后单词不是叶子节点，压根前面的所有压栈弹栈操作都是浪费的时间性能
     * 这样做貌似复杂了，其实完全可以记录下当时不可删除的节点，即满足cur.isWord || cur.next.size() > 1此条件的节点，如果最终需要删除，直接删除该节点以后的对应节点即可，没有必要级联往上追溯去删除
     */
    public boolean remove(String word) {
        Node cur = root;
        boolean flag = false;
        Stack<Object> stack = new ArrayStack<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            //如果条件成立则该节点为不可删除的节点
            if(cur.isWord || cur.next.size() > 1) {
                flag = true;
                for(;!stack.isEmpty();) {
                    stack.pop();
                }
            }
            //从该节点开始压栈
            if (flag) {
                stack.push(c);
                stack.push(cur);
            }
            cur = cur.next.get(c);
        }
        if (cur.isWord) {
            cur.isWord = false;
            size--;
            if(cur.next.size() == 0) {
                System.out.println(stack);
                for(;!stack.isEmpty();){
                    Node pop = (Node) stack.pop();
                    char c = (char) stack.pop();
                        //弹栈的每个节点都可以删除自己的子节点
                        pop.next.remove(c);
                }
            }
            return true;
        }
        return false;
    }

    public boolean isPrefix(String prefix) {
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

}