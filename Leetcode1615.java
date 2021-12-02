import java.util.SortedMap;
class Leetcode1615 {
    public int maximalNetworkRank(int n, int[][] roads) {
        int[] degreeArray = new int[n];
        TreeMap<Integer, Set<Integer>>  maxMap = new TreeMap<>(Comparator.reverseOrder()); 
        int max = -1 , secondMax = -1;
        for(int[] road : roads) {
            int u = road[0];
            int v = road[1];
            ++degreeArray[u];
            ++degreeArray[v];
            
        }
        for(int u = 0; u < n ; ++u) {
            if (degreeArray[u] >= max) {
                if (!maxMap.containsKey(degreeArray[u])) {maxMap.put(degreeArray[u] , new HashSet<>());}
                maxMap.get(degreeArray[u]).add(u);
                secondMax = max;
                max = degreeArray[u];
            } else  if (degreeArray[u] >= secondMax && degreeArray[u] < max) {
                if (!maxMap.containsKey(degreeArray[u])) {maxMap.put(degreeArray[u] , new HashSet<>());}
                maxMap.get(degreeArray[u]).add(u);
                secondMax = degreeArray[u];
            } 
           // if (degreeArray[v] >= max) {
           //      if (!maxMap.containsKey(degreeArray[v])) {maxMap.put(degreeArray[v] , new HashSet<>());}
           //      maxMap.get(degreeArray[v]).add(v);
           //     secondMax = max;
           //      max = degreeArray[v];
           //  } else if  (degreeArray[v] >= secondMax && degreeArray[v] < max) {
           //      if (!maxMap.containsKey(degreeArray[v])) {maxMap.put(degreeArray[v] , new HashSet<>());}
           //      maxMap.get(degreeArray[v]).add(v);
           //     // secondMax = max;
           //     secondMax = degreeArray[v];
           //  }        
            
        }
        int ans = 0;
        Set<Integer> allvertices = new HashSet<>();
        allvertices.addAll(maxMap.firstEntry().getValue());
        SortedMap smap = maxMap.tailMap(maxMap.firstKey(), false);
        if (!smap.isEmpty()) {
            allvertices.addAll(maxMap.get(smap.firstKey()));
        }

        for(Integer vertexu : allvertices) {
             for(Integer vertexv : allvertices) {
                // System.out.println(vertexu + "::" + vertexv + "::" + degreeArray[vertexu] + "::"+ degreeArray[vertexv] );
                if (vertexu != vertexv) {
                    boolean found = false;
                    for (int[] road : roads) {
                        int u = road[0];
                        int v = road[1];
                        if ((vertexu == u && vertexv == v) || ((vertexu == v && vertexv == u))) {
                            ans = Math.max(ans, degreeArray[vertexu] + degreeArray[vertexv] - 1);
                            found = true;
                            break;
                        } 
                    }
                    if (!found) ans = Math.max(ans, degreeArray[vertexu] + degreeArray[vertexv] );
                       
                   
                }
            }
        }
        return ans;
           
        
        
    }
}
