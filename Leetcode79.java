class Leetcode79 {
    
    
    int m;
    int n;
    Map<Node, Node> nodes = new HashMap<>();
    
    
    class Node {
        int r;
        int c;
        boolean visited;
        List<Node> edges = new ArrayList<>();
        // List<Node> visited = new ArrayList<>();
        Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
          @Override
        public boolean equals(Object o) {
            Node that = (Node) o;
            return this.r == that.r && this.c == that.c;
        }
        
        @Override
        public int hashCode() {
            return String.valueOf(this.r + "-" + this.c).hashCode();
        }
    }
    public boolean dfs(char[][] board, Node node, String word) {
        boolean found= false;
        
        Stack<Pair<Node,   String>> stack = new Stack<>();
        
         Stack<Pair<Node,  String>> bstack = new Stack<>();
        
        stack.push(new Pair<>(node, word));
        StringBuilder sb = new StringBuilder();
        Node prevNode = node;
        while(!stack.isEmpty() && word.length() >= 1) {
            Pair<Node, String> pair = stack.pop();
            Node cnode = pair.getKey();
            if (cnode.visited)
                continue;
           
            int i  = cnode.r;
            int j = cnode.c;
            word = new String(pair.getValue());
            
            String cur = String.valueOf(board[i][j]);
            
            String first = word.substring(0, 1);           
           
          
            if (cur.equals(first)) {
               
                if (word.length() == 1) {
                    found = true;
                    break;
                } else {
                    
                    boolean canmove = false;
                    String second = word.substring(1, 2);
                   
                    word = word.substring(1);
                    int moveCount = 0;
                    for(Node re : cnode.edges) {
                        if (!re.visited && String.valueOf(board[re.r][re.c]).equals(second) && 
                              (!re.equals(prevNode))) {
                                stack.push(new Pair<>(re, word));
                                ++moveCount;
                                canmove = true;
                        }
                    }
                    
                    
                    if (moveCount == 0) {
                        if (!bstack.isEmpty() && !stack.isEmpty()) {
                            Pair<Node,  String> bPair = bstack.peek();
                            Pair<Node,  String> sPair = stack.peek();
                            String bword = bPair.getValue().substring(1);
                            String sword = sPair.getValue();
                            while(!bstack.isEmpty() && !bword.equals(sword)) {
                                bPair = bstack.pop();
                                bword = bPair.getValue();
                                bPair.getKey().visited = false;
                            }                            
                        }                            
                    } else {
                        bstack.push(new Pair<>(cnode, pair.getValue()));
                         
                        cnode.visited = true;
                           
                         
                    }
                    
                   
                }
                prevNode = cnode;
                
                //System.out.println(i + "::" + j + "::" + word); 
                    
            } 
        
           
            
        }
        return found;
    }

    public boolean exist(char[][] board, String word) {
        
        m = board.length;
        n = board[0].length;
        
        // visited = new int[m][n];
        Set<Node> indices = new HashSet<>();
        
        
        String start = word.substring(0, 1);
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
                
                Node node = new Node(i, j);
                Node an = nodes.get(node);
                if (an == null) {
                    an = node;
                    nodes.put(an, an);
                }
                if (String.valueOf(board[i][j]).equals(start))
                    indices.add(an);
                if (i + 1 < m) {
                    int ri= i + 1;
                    int rj = j;
                    Node nr = new Node(ri, rj);
                    Node anr = nodes.get(nr);
                    if (anr == null) {
                        anr = nr;
                        nodes.put(anr, anr);
                    }
                    //Edge edgef = new Edge(i, j, ri, rj);
                    an.edges.add(anr);
                    // Edge edget = new Edge(ri, rj, i, j);
                    // anr.edges.add(edget);
                }
                if (i - 1 >= 0) {
                    int li = i - 1;
                    int lj = j;
                    Node nl = new Node(li, lj);
                    Node anl = nodes.get(nl);
                    if (anl == null) {
                        anl = nl;
                        nodes.put(anl, anl);
                    }
                   // Edge edgef = new Edge(i, j, li, lj);
                    an.edges.add(anl);
                    // Edge edget = new Edge(li, lj, i, j);
                    // anl.edges.add(edget);
                }
                if (j + 1 < n) {
                    int dj= j + 1;
                    int di = i;
                    Node nd = new Node(di, dj);
                    Node and = nodes.get(nd);
                    if (and == null) {
                        and = nd;
                        nodes.put(and, and);
                    }
                   // Edge edgef = new Edge(i, j, di, dj);
                    an.edges.add(and);
                    // Edge edget = new Edge(di, dj, i, j);
                    // and.edges.add(edget);
                }
                if (j - 1 >= 0) {
                    int uj = j - 1;
                    int ui = i;

                    Node nu = new Node(ui, uj);
                    Node anu = nodes.get(nu);
                    if (anu == null) {
                        anu = nu;
                        nodes.put(anu, anu);
                    }
                   // Edge edgef = new Edge(i, j, ui, uj);
                    an.edges.add(anu);
                    // Edge edget = new Edge(ui, uj, i, j);
                    // anu.edges.add(edget);
                }
                
            }
        }
        boolean found= false;
        for(Node node: indices) {
            for(Map.Entry<Node, Node> entry: nodes.entrySet()) {
                entry.getKey().visited = false;
            }
            found = dfs(board, node, word);
            if (found)
                break;
            
        }
        return found;
    }
}
