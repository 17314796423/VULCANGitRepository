package set;

import java.util.Set;
import java.util.TreeSet;

public class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        String[] code = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        Set<String> set = new TreeSet<>();
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if(sb.length() > 0)
                sb.delete(0,sb.length());
            for (int i = 0; i < word.length(); i++)
                sb.append(code[word.charAt(i) - 'a']);
            set.add(sb.toString());
        }
        return set.size();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().uniqueMorseRepresentations(new String[]{"gin", "zen", "gig", "msg"}));
    }
}