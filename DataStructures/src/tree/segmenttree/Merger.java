package tree.segmenttree;

public interface Merger<E> {

    E merge(E leftChildNode, E rightChildNode);

}
