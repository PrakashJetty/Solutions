class Leetcode959 {
     int cycles = 0;
    int edges;
    int vertices;
    class Node {
        int row;
        int col;
        boolean cyclescalculated = false;
        boolean visited = false;
        List<Node> edges = new ArrayList<>();
        Node from;
        Node to;
        int visitedCount = 0;
        int cycleColor;
        
        public  Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public boolean equals(Object o) {
            Node other = (Node)o;
            return other.row == this.row && other.col == this.col;
        }
        
        @Override
        public int hashCode() {
            return String.valueOf(row + ":" + col).hashCode();
        }
    }
    
    public void dfs(Node u, Node from) {
        if (u.visitedCount == 2)
            return;
        if (u.visitedCount == 1) {
            cycles++;
			Node cur = from;
			cur.cycleColor = cycles;

			
			while (!cur.equals(u))
			{
				cur = cur.from;
				cur.cycleColor = cycles;
			}
			return;
        }
        u.from = from;

		u.visitedCount = 1;

		for (Node v : u.edges) {

			if (from != null && v.equals(from)) {
				continue;
			}
			dfs(v, u);
		}

		u.visitedCount = 2;
    }
    

    
      
    public int regionsBySlashes(String[] grid) {
        
        int n = grid.length;
        HashSet<Node> graph = new HashSet<>();
        HashSet<Node> borderNodes = new HashSet<>();
        int col  = 0, rows = 0;
        for (int i = 0; i < n ; ++i) {
            String gridString = grid[i];
            char[] chars = gridString.toCharArray();
            int index = 1;
//          
            int j = 0;
            Node  cornerNode1 = new Node(0 , 0);
            borderNodes.add(cornerNode1);
             Node  cornerNode2 = new Node(0 , n );
            borderNodes.add(cornerNode2);
             Node  cornerNode3 = new Node(n , 0);
            borderNodes.add(cornerNode3);
             Node  cornerNode4 = new Node(n , n);
            borderNodes.add(cornerNode4);
            for (char ch : chars) {
                if (ch == '/') {
                    Node  spoint = new Node(i + 1 , j);
                    if (i + 1 == 0 ||  i + 1 == n || j == 0 ||  j == n)
                        borderNodes.add(spoint);
                   
                    Node dpoint = new Node(i, j + 1);
                    if (i  == 0 ||  i  == n || j + 1 == 0 ||  j + 1 == n)
                        borderNodes.add(dpoint);
                    graph.add(spoint);
                    graph.add(dpoint);
                    ++edges;                    
                }
                if (ch == '\\') {
                    Node  spoint = new Node(i  , j );
                    if (i  == 0 ||  i  == n || j  == 0 ||  j == n)
                        borderNodes.add(spoint);
                    Node dpoint = new Node(i + 1 , j + 1);
                    if (i + 1 == 0 ||  i + 1 == n || j + 1 == 0 ||  j + 1 == n)
                        borderNodes.add( dpoint);
                    graph.add(spoint);
                    graph.add(dpoint);
                    ++edges;                    
                }
                ++j;
            }
        }
        
        edges += borderNodes.size() - 1;
        graph.addAll(borderNodes);
        

        
//         int ans = 0;
//         System.out.println("    Start               ");
//         graph.stream().forEach(nd -> System.out.print(nd.row + "," + nd.col + "::"));
//         System.out.println("    End           ");
//           System.out.println("    Start               ");
//         borderNodes.stream().forEach(nd -> System.out.print(nd.row + "," + nd.col+ "::"));
//         System.out.println("    End           ");

            
        return edges + 2 - (graph.size() ) ;
       
        
    }
}
