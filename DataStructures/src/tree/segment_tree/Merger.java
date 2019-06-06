package tree.segment_tree;

public interface Merger<E> {

    E merge(E leftChildNode, E rightChildNode);

}
