package algorithms.graph;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Graph dg = new DenseGraph(20, false);
        Graph sg = new SparseGraph(20, false);
        new RandomGraph(dg, 20, 100);
        new RandomGraph(sg, 20, 100);
        for (int i = 0; i < dg.V(); i++) {
            Iterator<Integer> it = dg.adj(i).iterator();
            System.out.print("vertex " + i + " : ");
            while (it.hasNext()) {
                System.out.print(it.next() + "\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < dg.V(); i++) {
            Iterator<Integer> it = sg.adj(i).iterator();
            System.out.print("vertex " + i + " : ");
            while (it.hasNext()) {
                System.out.print(it.next() + "\t");
            }
            System.out.println();
        }
    }
}
