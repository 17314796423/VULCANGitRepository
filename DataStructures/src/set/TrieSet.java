package set;

import tree.trie.Trie;

public class TrieSet implements Set<String> {

    private Trie trie;

    public TrieSet(){
        this.trie = new Trie();
    }

    @Override
    public void add(String o) {
        trie.add(o);
    }

    @Override
    public boolean contains(String o) {
        return trie.contains(o);
    }

    @Override
    public void remove(String o) {
        trie.remove(o);
    }

    @Override
    public int getSize() {
        return trie.size();
    }

    @Override
    public boolean isEmpty() {
        return trie.isEmpty();
    }
}
