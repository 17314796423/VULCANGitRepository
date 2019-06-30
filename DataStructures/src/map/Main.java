package map;

import hashtable.HashTable;
import set.FileOperation;

import java.util.ArrayList;

public class Main {

    private static double testMap(Map<String, Integer> map, String filename){

        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            for (String word : words){
                if(map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.put(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        String filename = "pride-and-prejudice.txt";

        BSTreeMap<String, Integer> bstMap = new BSTreeMap<>();
        double time1 = testMap(bstMap, filename);
        System.out.println("BST Map: " + time1 + " s");

        System.out.println();

        TrieMap<Integer> trieMap = new TrieMap<>();
        double time2 = testMap(trieMap, filename);
        System.out.println("Trie Map: " + time2 + " s");

        System.out.println();

        LinkedListMap<String, Integer> linkedListMap = new LinkedListMap<>();
        double time3 = testMap(linkedListMap, filename);
        System.out.println("Linked List Map: " + time3 + " s");

        System.out.println();

        AVLMap<String, Integer> avlMap = new AVLMap<>();
        double time4 = testMap(avlMap, filename);
        System.out.println("AVL Map: " + time4 + " s");

        System.out.println();
        //131071
        HashTable<String, Integer> ht = new HashTable<>();
        double time5 = testMap(ht, filename);
        System.out.println("HashTable Map: " + time5 + " s");

    }
}
