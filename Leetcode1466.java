class Leetcode1466 {
    int count;
    class Node {
        int val;
        boolean visited = false;
        boolean ivisited = false;
        boolean rightDirection = false;
        List<Node> inedges = new ArrayList<>();
        List<Node> outedges = new ArrayList<>();
        public  Node(int val) {
            this.val = val;
            
        }
        
        @Override
        public boolean equals(Object o) {
            Node other = (Node)o;
            return other.val == this.val;
        }
        
        @Override
        public int hashCode() {
            return String.valueOf(val).hashCode();
        }
    }
    
    public int dfs(Node src) {
        if (!src.visited) {
            src.visited = true; 
            List<Node> inedges = src.rightDirection ? src.inedges : src.outedges;
            List<Node> outedges = src.rightDirection ? src.outedges : src.inedges;
            // List<Node> edges = new ArrayList<>();
            // edges.addAll(oedges);
            // edges.addAll(inedges);
            for(Node s : inedges) {
                if (!s.visited) {
                    if (src.rightDirection) {
                        s.rightDirection = true;
                        count = dfs(s);
                    } else {
                        System.out.println("yes" + s.val);
                        count  = 1 + dfs(s);
                    }
                    
                }
            }
            for(Node s : outedges) {
                if (!s.visited) {  
                    if (src.rightDirection) {
                        System.out.println("yes" + s.val);
                        count  = 1 + dfs(s);
                    } else {
                        s.rightDirection = true;
                        count = dfs(s);
                    }
                    
                }
            }
        }
        return count;
    }
    public int minReorder(int n, int[][] edges) {
        Node src = null;
        Map<Node, Node> graph = new HashMap<>();
        for (int i = 0; i < edges.length ; ++i) {
            int u = edges[i][0];
            int v = edges[i][1];
            Node unode = new Node(u);
            Node vnode = new Node(v);
            Node munode = graph.get(unode);
            if (munode == null) {
                graph.put(unode, unode);
                munode = unode;
            } else {
                unode = munode;
            }
            Node mvnode = graph.get(vnode);
            if (mvnode == null) {
                graph.put(vnode, vnode);
                mvnode = vnode;
            } else {
                vnode = mvnode;
            }
            munode.outedges.add(mvnode);
            mvnode.inedges.add(munode);
            if (u == 0)
                src = munode;
            if (v == 0)
                src = mvnode;
           
        }
        src.rightDirection = true;
        count = dfs(src);
        return count;
        
    }
}
