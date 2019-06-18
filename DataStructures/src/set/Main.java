package set;

import java.util.ArrayList;

public class Main {

    private static double testSet(Set<String> set, String filename){

        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            for (String word : words)
                set.add(word);
            System.out.println("Total different words: " + set.getSize());
        }
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        String filename = "pride-and-prejudice.txt";

        BSTreeSet<String> bstSet = new BSTreeSet<>();
        double time1 = testSet(bstSet, filename);
        System.out.println("BST Set: " + time1 + " s");

        System.out.println();

        LinkedListSet<String> linkedListSet = new LinkedListSet<>();
        double time2 = testSet(linkedListSet, filename);
        System.out.println("Linked List Set: " + time2 + " s");

        System.out.println();

        TrieSet trieSet = new TrieSet();
        double time3 = testSet(trieSet, filename);
        System.out.println("Trie Set: " + time3 + " s");

        System.out.println();

        AVLSet avlSet = new AVLSet();
        double time4 = testSet(avlSet, filename);
        System.out.println("AVL Set: " + time4 + " s");

    }
}
