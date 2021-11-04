public class Leetcode1525 {

    public int numSplits(String s) {
        
        Map<Character, Boolean> leftMap = new HashMap<>();
        Map<Character, Set<Integer>> rightMap = new HashMap<>();
        int leftCount = 0, rightCount = 0, count = 0;
        
        char[] aplhs = s.toCharArray(); 
        int index = 0;
         for (char c : aplhs) {
            if (!rightMap.containsKey(c)) {
                ++rightCount;
                Set<Integer> indexes = new HashSet<>();
                indexes.add(index);
                rightMap.put(c, indexes);                
            } else {
                rightMap.get(c).add(index);
            }
             ++index;
            
        }
        index = 0;
        for (char c : aplhs) {
            if (!leftMap.containsKey(c)) {
                ++leftCount;
                leftMap.put(c, true);                
            }
            rightMap.get(c).remove(index);
            if (rightMap.get(c).size() == 0) {
                rightMap.remove(c);
                --rightCount;
            } 
            ++index;
            if (rightCount == leftCount)
                ++count;
        }
        
        return count;
        
    }
}