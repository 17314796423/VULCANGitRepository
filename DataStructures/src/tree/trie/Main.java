package tree.trie;

import set.BSTreeSet;
import set.FileOperation;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("The-Decameron-Giovanni-Boccaccio.txt", words)) {

            long startTime = System.nanoTime();

            BSTreeSet<String> set = new BSTreeSet<>();
            for (String word : words)
                set.add(word);

            for (String word : words)
                set.contains(word);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + set.getSize());
            System.out.println("BSTSet: " + time + " s");

            // ---

            startTime = System.nanoTime();

            Trie trie = new Trie();
            for (String word : words)
                trie.add(word);

            for (String word : words)
                trie.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + trie.size());
            System.out.println("Trie: " + time + " s");

            // ---

            startTime = System.nanoTime();

            TrieRec trieRec = new TrieRec();
            for (String word : words)
                trieRec.add(word);

            for (String word : words)
                trieRec.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + trie.size());
            System.out.println("TrieRec: " + time + " s");

        }
    }
}
