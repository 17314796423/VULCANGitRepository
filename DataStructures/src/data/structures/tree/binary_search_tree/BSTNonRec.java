package data.structures.tree.binary_search_tree;

import data.structures.queue.LoopQueue;
import data.structures.queue.Queue;
import data.structures.stack.LinkedListStack;
import data.structures.stack.Stack;

public class BSTNonRec<E extends Comparable<E>> {

    private class Node {
        public E e;
        public Node left;
        public Node right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return this.e.toString();
        }
    }

    private Node root;
    private int size;

    public BSTNonRec() {
        this.root = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(E e) {
        Node node = root;
        for (; node != null; ) {
            if (e.compareTo(node.e) == 0)               //说明已有该节点，不做任何操作
                return;
            else if (e.compareTo(node.e) < 0) {         //应该在当前节点左子树添加节点
                if (node.left != null) {                //如果左子树不为空，则进入左子树，进入下一次循环
                    node = node.left;
                    continue;
                }
                node.left = new Node(e);                //说明应该在此处添加节点
            } else if (e.compareTo(node.e) > 0) {       //应该在当前节点右子树添加节点
                if (node.right != null) {               //如果右子树不为空，则进入右子树，进入下一次循环
                    node = node.right;
                    continue;
                }
                node.right = new Node(e);               //说明应该在此处添加节点
            }
            size++;
            return;
        }
        root = new Node(e);                             //如果没进循环，说明为空树，直接根节点添加
        size++;
    }

    public boolean contains(E e) {
        Node node = root;
        for (; node != null; ) {
            if (e.compareTo(node.e) < 0) {              //左子树查找
                node = node.left;
                continue;
            } else if (e.compareTo(node.e) > 0) {       //右子树查找
                node = node.right;
                continue;
            }
            return true;                                //找到该元素
        }
        return false;
    }

    public void prevTraverse() {
        Stack<Node> stack = new LinkedListStack<>();
        if (root != null)
            stack.push(root);
        for (; !stack.isEmpty(); ) {
            Node pop = stack.pop();
            System.out.println(pop.e);
            if (pop.right != null)
                stack.push(pop.right);
            if (pop.left != null)
                stack.push(pop.left);
        }
    }

    public void midTraverse() {
        Stack<Node> stack = new LinkedListStack<>();            //深度优先压栈到左子树底部每弹栈一个元素遍历之后判断是否有右子树，如果有右子树优先遍历右子树
        Node node = root;
        for (; !stack.isEmpty() || node != null; ) {
            for (; node != null; ) {
                stack.push(node);
                node = node.left;
            }
            Node pop = stack.pop();
            System.out.println(pop.e);
            node = pop.right;
        }
    }

    public void postTraverse() {
        Stack<Node> stack = new LinkedListStack<>();            //深度优先压栈到左子树底部每查看一个栈顶元素是否有右子树，如果没有右子树直接遍历栈顶元素，做上已遍历标记后弹栈，如果有右子树优先压栈后查看右子树是否满足可遍历条件
        Node node = root;
        Node pre = null;
        for (; !stack.isEmpty() || node != null; ) {
            for (; node != null; ) {
                stack.push(node);
                node = node.left;
            }
            Node peek = stack.peek();
            if (peek.right == null || peek.right == pre) {
                System.out.println(stack.pop());
                pre = peek;
            } else {
                node = peek.right;
            }
        }
    }

    public void levelTraverse() {
        if (size != 0) {
            Queue<Node> queue = new LoopQueue<>();
            queue.enqueue(root);
            for (; !queue.isEmpty(); ) {
                Node cur = queue.dequeue();
                System.out.println(cur.e);
                if (cur.left != null) {
                    queue.enqueue(cur.left);
                }
                if (cur.right != null) {
                    queue.enqueue(cur.right);
                }
            }
        }
    }

    public E floor(E e){
        Stack<Node> stack = new LinkedListStack<>();
        Node node = root;
        Node prev = null;
        for (; !stack.isEmpty() || node != null; ) {
            for (; node != null; ) {
                stack.push(node);
                node = node.left;
            }
            Node pop = stack.pop();
            if(e.compareTo(pop.e) <= 0){
                return prev == null?null:prev.e;
            }
            prev = pop;
            node = pop.right;
        }
        return prev == null?null:prev.e;
    }

    public E ceil(E e){
        Stack<Node> stack = new LinkedListStack<>();
        Node node = root;
        //boolean flag = false;
        for (; !stack.isEmpty() || node != null; ) {
            for (; node != null; ) {
                stack.push(node);
                node = node.left;
            }
            Node pop = stack.pop();
            if(/*flag || */e.compareTo(pop.e) < 0)
                return pop.e;
            /*if(e.compareTo(pop.e) == 0){
                flag = true;
            }*/
            node = pop.right;
        }
        return null;
    }

    public int rank(E e){
        Stack<Node> stack = new LinkedListStack<>();
        Node node = root;
        int rank = 1;
        for (; !stack.isEmpty() || node != null; ) {
            for (; node != null; ) {
                stack.push(node);
                node = node.left;
            }
            Node pop = stack.pop();
            if(e.compareTo(pop.e) == 0)
                return rank;
            if(e.compareTo(pop.e) < 0)
                throw new IllegalArgumentException("element is not exist!");
            rank ++;
            node = pop.right;
        }
        throw new IllegalArgumentException("element is not exist!");
    }

    public E select(int rank){
        if (isEmpty())
            throw new IllegalArgumentException("BST is empty");
        if (rank > size || rank < 1)
            throw new IllegalArgumentException("rank range must between 1 and " + size);
        Stack<Node> stack = new LinkedListStack<>();
        Node node = root;
        int count = 1;
        for (; !stack.isEmpty() || node != null; ) {
            for (; node != null; ) {
                stack.push(node);
                node = node.left;
            }
            Node pop = stack.pop();
            if(count == rank)
                return pop.e;
            count ++;
            node = pop.right;
        }
        return null;
    }

    public E minimum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return minimum(root);
    }

    private E minimum(Node node) {
        for (; node.left != null; ) {
            node = node.left;
        }
        return node.e;
    }

    public E maximum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return maximum(root);
    }

    private E maximum(Node node) {
        for (; node.right != null; ) {
            node = node.right;
        }
        return node.e;
    }

    //以下为自己实现的删除最小最大节点以及被删除节点的子节点一同删除的方法
    /*public E removeMin() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        Node node = root;
        Node prev = null;
        for (; node.left != null; ) {
            prev = node;
            node = node.left;
        }
        size -= getNodeCount(node);
        if (prev != null) {
            prev.left = null;
            return node.e;
        }
        root = null;
        return node.e;
    }

    public E removeMax() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        Node node = root;
        Node prev = null;
        for (; node.right != null; ) {
            prev = node;
            node = node.right;
        }
        size -= getNodeCount(node);
        if (prev != null) {
            prev.right = null;
            return node.e;
        }
        root = null;
        return node.e;
    }*/

    //以下为自己实现的删除最大最小节点并挂接后续子节点至被删除位置，但是对根节点需要另外处理
    public E removeMin() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return removeMin(root);
    }

    private E removeMin(Node node) {
        Node prev = null;
        for (; node.left != null; ) {
            prev = node;
            node = node.left;
        }
        size --;
        Node newNode = node.right;
        node.right = null;
        if (prev != null) {
            prev.left = newNode;
            return node.e;
        }
        root = newNode;
        return node.e;
    }

    public E removeMax() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return removeMax(root);
    }

    private E removeMax(Node node) {
        Node prev = null;
        for (; node.right != null; ) {
            prev = node;
            node = node.right;
        }
        size --;
        Node newNode = node.left;
        node.left = null;
        if (prev != null) {
            prev.right = newNode;
            return node.e;
        }
        root = newNode;
        return node.e;
    }

    public void remove(E e){
        Node node = root;
        Node prev = null;                       //记录被删除节点的上一个节点的临时变量
        boolean flag = true;                    //记录被删除节点是上一个节点的左子树还是右子树的临时变量
        for(;node != null;){
            if(e.compareTo(node.e) < 0) {       //判断是否应该去左子树查找
                prev = node;
                flag = true;
                node = node.left;
            }else if (e.compareTo(node.e) > 0) {//判断是否应该去右子树查找
                prev = node;
                flag = false;
                node = node.right;
            }else{                              //确认当前节点就是要被删除的节点
                if(node.left != null && node.right == null){            //当前节点的左子树不为空直接将左子树挂接到上一个节点的后面
                    size --;
                    Node newNode = node.left;
                    node.left = null;
                    if(prev == null){
                        root = newNode;
                        return;
                    }
                    if(flag)
                        prev.left = newNode;
                    else
                        prev.right = newNode;
                    return;
                }else if(node.left == null && node.right != null){      //当前节点的右子树不为空直接将右子树挂接到上一个节点的后面
                    size --;
                    Node newNode = node.right;
                    node.right = null;
                    if(prev == null){
                        root = newNode;
                        return;
                    }
                    if(flag)
                        prev.left = newNode;
                    else
                        prev.right = newNode;
                    return;
                }else if(node.left != null && node.right != null){      //此处复用了removeMin的逻辑，删除了被删除的节点的右子树的最小值，并将右子树最小值赋替换到该被删除的节点位置上
                    prev = null;
                    Node nod = node.right;
                    for (; nod.left != null; ) {
                        prev = nod;
                        nod = nod.left;
                    }
                    size --;
                    Node newNode = nod.right;
                    nod.right = null;
                    if (prev != null)
                        prev.left = newNode;
                    else
                        node.right = newNode;
                    node.e = nod.e;
                    return;
                }else {                                                 //当前节点左右子树均为空，直接将null挂接到上一个节点的后面
                    size --;
                    if(flag)
                        prev.left = null;
                    else
                        prev.right = null;
                    return;
                }
            }
        }
    }

    /**
     * 从当前节点为根节点计数以下有多少子节点
     * @param node
     * @return
     */
    private int getNodeCount(Node node) {
        int count = 0;
        Stack<Node> stack = new LinkedListStack<>();
        if (node != null)
            stack.push(node);
        for (; !stack.isEmpty(); ) {
            count ++;
            Node pop = stack.pop();
            if (pop.right != null)
                stack.push(pop.right);
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        //int[] arr = new int[]{15, 87, 34, 99, 11, 56, 71, 20};
        int[] arr = new int[]{21, 9, 10, 41, 23};
        //int[] arr = new int[]{};
        BSTNonRec<Integer> integerBST = new BSTNonRec<>();
        for (int i = 0; i < arr.length; i++) {
            integerBST.add(arr[i]);
        }
        /*System.out.println("prev:");
        integerBST.prevTraverse();*/
        System.out.println("mid:");
        integerBST.midTraverse();
        int num = 45;
        System.out.println(num + "的floor是" + integerBST.floor(num));
        System.out.println(num + "的ceil是" + integerBST.ceil(num));
        System.out.println(num + "的rank是" + integerBST.rank(num));
        System.out.println(1 + "的select是" + integerBST.select(1));
        /*System.out.println("post:");
        integerBST.postTraverse();
        System.out.println("level:");
        integerBST.levelTraverse();
        System.out.println("minimum:" + integerBST.minimum());
        System.out.println("maximum:" + integerBST.maximum());
        integerBST.removeMin();
        integerBST.prevTraverse();
        System.out.println("size:" + integerBST.size);
        integerBST.removeMax();
        integerBST.prevTraverse();
        System.out.println("size:" + integerBST.size);
        integerBST.remove(34);
        integerBST.prevTraverse();
        System.out.println("size:" + integerBST.size);*/
    }

}