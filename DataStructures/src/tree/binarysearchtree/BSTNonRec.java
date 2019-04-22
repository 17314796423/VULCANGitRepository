package tree.binarysearchtree;

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
		public String toString(){
			return this.e.toString();
		}
	}

	private Node root;
	private int size;

	public BSTNonRec(){
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
		Node node = root;
		for(;node != null;){
			if(e.compareTo(node.e) == 0)
				return;
			else if(e.compareTo(node.e) < 0){
				if (node.left != null) {
					node = node.left;
					continue;
				}
				node.left = new Node(e);
			}
			else if(e.compareTo(node.e) > 0){
				if (node.right != null) {
					node = node.right;
					continue;
				}
				node.right = new Node(e);
			}
			size ++;
			return;
		}
		root = new Node(e);
		size ++;
	}

	public static void main(String[] args){
		System.out.println(new BSTNonRec<Integer>().isEmpty());
	}

}