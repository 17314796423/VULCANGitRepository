package tree.red_black_tree;

import map.BSTreeMap;
import set.FileOperation;
import tree.avl.AVLTree;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("The Decameron Giovanni Boccaccio");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("The-Decameron-Giovanni-Boccaccio.txt", words)) {
            System.out.println("Total words: " + words.size());

            // Collections.sort(words);

            // Test BST
            long startTime = System.nanoTime();

            BSTreeMap<String, Integer> bst = new BSTreeMap<>();
            for (String word : words) {
                if (bst.contains(word)) {
                    bst.set(word, bst.get(word) + 1);
                } else {
                    bst.put(word, 1);
                }
            }

            for(String word: words) {
                bst.contains(word);
            }

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1000000000.0;
            System.out.println("BST: " + time + " s");


            // Test AVL
            startTime = System.nanoTime();

            AVLTree<String, Integer> avl = new AVLTree<>();
            for (String word : words) {
                if (avl.contains(word)) {
                    avl.set(word, avl.get(word) + 1);
                } else {
                    avl.add(word, 1);
                }
            }

            for(String word: words) {
                avl.contains(word);
            }

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("AVL: " + time + " s");


            // Test RBTree
            startTime = System.nanoTime();

            RBTree<String, Integer> rbt = new RBTree<>();
            for (String word : words) {
                if (rbt.contains(word)) {
                    rbt.set(word, rbt.get(word) + 1);
                } else {
                    rbt.put(word, 1);
                }
            }

            for(String word: words) {
                rbt.contains(word);
            }

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("RBTree: " + time + " s");
        }

        System.out.println();
    }
}