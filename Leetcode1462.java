import java.util.*;
class Leetcode1462 {
    boolean[] visited;
    Map<Integer, Set<Integer>> gmap  = new HashMap<>();   
   
    
    public void bfs(int src) {
        if (!visited[src]) {
            visited[src] = true;
            Set<Integer> sset =  gmap.get(src);
            if (sset != null) {
                for (Integer s : sset) {
                    bfs(s);                    
                }
                Set<Integer> mset =  new HashSet<>();
                for (Integer s : sset) {
                    mset.add(s);
                    if (gmap.get(s) != null)
                        mset.addAll(gmap.get(s));                    
                }
                gmap.put(src, mset);
            }
                
            
        }
        
    }
    
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        int n = prerequisites.length;
        visited = new boolean[numCourses];
        Arrays.fill(visited , false);
        List<Boolean> ans = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            Set<Integer> uset = gmap.get(u);
            if (uset == null) {
                uset = new HashSet<>();
                gmap.put(u, uset);
            }
            uset.add(v);
        }
        
        for(Map.Entry<Integer, Set<Integer>> entry : gmap.entrySet()) {
            int s = entry.getKey();
            if (!visited[s])
                bfs(s);
        }        
        
        for (int i =0; i < queries.length; ++i) {
            int src = queries[i][0];
            int dest = queries[i][1];
            Set<Integer> sset =  gmap.get(src);
            if (sset != null)            
                ans.add(sset.contains(dest));
            else
                ans.add(false);
        }
        return ans;
    }
}
