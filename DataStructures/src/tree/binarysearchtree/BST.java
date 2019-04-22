package tree.binarysearchtree;

public class BST<E extends Comparable<E>> {

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
		public String toString(){
			return this.e.toString();
		}
	}
	
	private Node root;
	private int size;
	
	public BST(){
		this.root = null;
		this.size = 0;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public boolean isEmpty(){
		return this.size == 0;
	}

	public void add(E e){
		root = add(e, root);
	}

	private Node add(E e, Node node){
		if (node != null) {
			if(e.compareTo(node.e) < 0)
				node.left = add(e, node.left);
			else if(e.compareTo(node.e) > 0)
				node.right = add(e, node.right);
			return node;
		}
		size ++;
		return new Node(e);
	}

	public static void main(String[] args){
		System.out.println(new BST<Integer>().isEmpty());
	}

}