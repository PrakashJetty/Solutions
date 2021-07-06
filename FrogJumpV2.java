public class FrogJumpV2 {


   public boolean canCross(int[] stones) {
        if (stones.length == 2) {
            if (stones[0] + 1 != stones[1])
                return false;
            else
                return true;
        }
         int n = stones.length;
        Map<Integer, Map<Integer, Integer>> smap = new HashMap<>();
        Map<Integer, Integer> jmap = new HashMap<>();
        Map<Integer, Integer> idxmap = new HashMap<>();
        jmap.put(1, 1);
        smap.put(0, jmap);
        for (int i = 0; i < n; ++i) {
            idxmap.put(stones[i], i);
        }

        boolean reached = false;
        for (int j = 0; j < n; ++j) {
            if (reached)
                break;
            jmap = smap.get(stones[j]);
            if (jmap != null && !jmap.isEmpty()) {
                for (Map.Entry<Integer, Integer> entry : jmap.entrySet()) {
                    if (reached)
                        break;
                    int jumpVal = entry.getKey();
                    int cstone = entry.getValue();
                    Set<Integer> jumpVals = new HashSet<>();
                    jumpVals.add(jumpVal);
                    if (jumpVal > 1)
                        jumpVals.add(jumpVal -1);
                    jumpVals.add(jumpVal + 1);
                    Map<Integer, Integer> cstoneMap  = smap.get(cstone);
                    if (cstoneMap == null) {
                        cstoneMap = new HashMap<>();
                        smap.put(cstone, cstoneMap);
                    } else {
                        Map<Integer, Integer> finalCstoneMap = cstoneMap;
                        jumpVals = jumpVals.stream().filter(jv -> !finalCstoneMap.containsKey(jv)).collect(Collectors.toSet());
                    }
                    for (int jv : jumpVals){
                        int nstone = cstone + jv;
                        if (nstone == stones[n -1]) {
                            reached = true;
                            break;
                        }
                        if (idxmap.containsKey(nstone)) {
                            cstoneMap.put(jv, nstone);
                        }
                    }
                }

            }
        }
        return reached;
    }

}