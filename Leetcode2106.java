class Leetcode2106 {
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
       TreeMap<Integer, Integer> lmap = new TreeMap<>();
       TreeMap<Integer, Integer> rmap = new TreeMap<>(); 
       int atF = 0;
       for(int[] pos : fruits) {
           int fruit = pos[1];
           int p = pos[0];
           if (startPos > p) {
               int ldelta = startPos - p ;
               if (ldelta <= k) {
                   lmap.put(ldelta, fruit);                   
               }
               
           } else if (p > startPos) {
               int rdelta = p - startPos ;
               if (rdelta <= k) {
                   rmap.put(rdelta, fruit);
               }
           } else {
               atF = fruit;
           }
       }
        int max = 0;
        TreeMap<Integer, Integer> lmapfar = new TreeMap<>();
        int lsum = 0;
       for(Map.Entry<Integer, Integer> entry : lmap.entrySet()) {
           lsum += entry.getValue();
           lmapfar.put(entry.getKey(), lsum);
       }
       TreeMap<Integer, Integer> rmapfar = new TreeMap<>();
       int rsum = 0;
       for(Map.Entry<Integer, Integer> entry : rmap.entrySet()) {
           rsum += entry.getValue();
           rmapfar.put(entry.getKey(), rsum);
       }
        
       for(Map.Entry<Integer, Integer> entry : lmapfar.entrySet()) {
           int delta = entry.getKey();
           int kdelta = k - (2 * delta);
           lsum = entry.getValue();
           if (kdelta >= 0) {
               Map.Entry<Integer, Integer> fentry = rmapfar.floorEntry(kdelta);

               if (fentry != null) {
                   
                   rsum = fentry.getValue();
                   //System.out.println("L=" + delta + "::" + kdelta + "::" + fentry.getKey() + "::"+ rsum);

                   max = Math.max(max, lsum + rsum);
               } else {
                   max = Math.max(max, lsum );
               }
           } else {
                   max = Math.max(max, lsum );
            }
           
           
       }
       for(Map.Entry<Integer, Integer> entry : rmapfar.entrySet()) {
           int delta = entry.getKey();
           int kdelta = k - (2 * delta);
           rsum = entry.getValue();
           if (kdelta >= 0) {
               Map.Entry<Integer, Integer> fentry = lmapfar.floorEntry(kdelta);

               if (fentry != null) {
                   
                   lsum = fentry.getValue();
                   //System.out.println("R =" + delta + "::" + kdelta + "::" + fentry.getKey() + "::"+ lsum);
                   max = Math.max(max, lsum + rsum);
               } else {
                   max = Math.max(max, rsum );
               }
           } else {
                   max = Math.max(max, rsum );
            }
           
       }
        return max + atF;
    }
}
