class Leetcode959 {
    class Node {
        int row;
        int col;
        boolean cyclescalculated = false;
        boolean visited = false;
        List<Node> edges = new ArrayList<>();
        
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
    
    public int dfs(Node srcNode) {
        Stack<Node> stack = new Stack<>();
        int cycles = 0;
        srcNode.edges.forEach(n -> {if (!n.visited) {
                    stack.push(n);               
                }});
         Node node = null;
        while (!stack.isEmpty()) {
            node = stack.pop();
            node.visited = true;
            if (node.equals(srcNode))
                ++cycles;
            else
                node.edges.forEach(n -> {if (!n.visited) {
                    stack.push(n);               
                }});
        }
        return cycles;
    }
    
      
    public int regionsBySlashes(String[] grid) {
        
        int n = grid.length;
        Map<Node , Node> graph = new HashMap<>();
        int col  = 0, rows = 0;
        for (int i = 0; i < n ; ++i) {
            String gridString = grid[i];
            char[] chars = gridString.toCharArray();
            int index = 1;
            int quad = i <= 7 ? 1 : ((i >= 8 && i <= 15) ?
                                     2 : ((i >= 16 && i <= 23) ? 3 : 4));
            int row = (quad == 1 || quad == 3) ?  ((i <= 7 && i % 2 == 0) || (i >= 16 && i <= 23 && i % 2 ==0 ) ? 0 : 1) : 
                                     ((((i >= 8 && i <= 15 && i % 2 == 0)) || (i >= 24 && i <= 31 &&  i %2 == 0) ) ? 2 : 3);
            rows = Math.max(rows, row);
             if(quad == 1 && i <= 15 && row == 0) {
                 col = i / 2;
             }  
             if (quad == 3 && i >=16 && i <= 31 && row == 0) {
                 col = 4 +  (i % 4 ) /2; 
             }
            
            for (char ch : chars) {
                if (ch == '/') {
                    if (index == 1) {
                        Node  spoint = new Node((row + 1) * 2, col * 2);
                        Node dpoint = new Node(row  * 2, (col * 2) + 1);
                        Node mnode  = graph.get(spoint);
                        if (mnode == null) {
                            graph.put(spoint, spoint);
                            mnode = spoint;
                        } else {
                            spoint = mnode;
                        }
                        mnode.edges.add(dpoint);
                        Node mdnode = graph.get(dpoint);
                        if (mdnode == null) {
                            graph.put(dpoint, dpoint);
                            mdnode = dpoint;
                        } else {
                            dpoint = mdnode;
                        }
                        mdnode.edges.add(spoint);
                        
                        
                    } else {
                        Node  spoint = new Node((row + 1) * 2, (col * 2) + 1);
                        Node dpoint = new Node(row  * 2, (col + 1) * 2);
                        Node mnode  = graph.get(spoint);
                        if (mnode == null) {
                            graph.put(spoint, spoint);
                            mnode = spoint;
                        } else {
                            spoint = mnode;
                        }
                        mnode.edges.add(dpoint);
                        Node mdnode = graph.get(dpoint);
                        if (mdnode == null) {
                            graph.put(dpoint, dpoint);
                            mdnode = dpoint;
                        } else {
                            dpoint = mdnode;
                        }
                        mdnode.edges.add(spoint);
                    }
                }
                if (ch == '\\') {
                    if (index == 1) {
                        Node  spoint = new Node((row) * 2, col * 2);
                        Node dpoint = new Node( (row + 1)  * 2, (col * 2) + 1);
                        Node mnode  = graph.get(spoint);
                        if (mnode == null) {
                            graph.put(spoint, spoint);
                            mnode = spoint;
                        } else {
                            spoint = mnode;
                        }
                        mnode.edges.add(dpoint);
                        Node mdnode = graph.get(dpoint);
                        if (mdnode == null) {
                            graph.put(dpoint, dpoint);
                            mdnode = dpoint;
                        } else {
                            dpoint = mdnode;
                        }
                        mdnode.edges.add(spoint);
                        
                        
                    } else {
                        Node  spoint = new Node((row) * 2, (col * 2) + 1);
                        Node dpoint = new Node((row + 1) * 2, (col + 1) * 2);
                         Node mnode  = graph.get(spoint);
                        if (mnode == null) {
                            graph.put(spoint, spoint);
                            mnode = spoint;
                        } else {
                            spoint = mnode;
                        }
                        mnode.edges.add(dpoint);
                        Node mdnode = graph.get(dpoint);
                        if (mdnode == null) {
                            graph.put(dpoint, dpoint);
                            mdnode = dpoint;
                        } else {
                            dpoint = mdnode;
                        }
                        mdnode.edges.add(spoint);
                    }
                }
                ++index;
            }
        }
        Node pspoint = null, prpoint = null;
        for(int i = 0; i <= rows + 1; ++ i) {
            int arow = i * 2;
            Node  spoint = new Node(arow, 0);
            if (!graph.containsKey(spoint)) {
                graph.put(spoint, spoint);
            }
            spoint = graph.get(spoint);
            if (arow + 1 < rows * 2) {
                Node rnode0 = new Node(arow + 1, 0);
                if (graph.containsKey(rnode0)) {
                    rnode0 = graph.get(rnode0);
                    spoint.edges.add(rnode0);
                    rnode0.edges.add(spoint);                
                }
            }
            if (arow - 1 > 0) {
                Node lnode0 = new Node(arow -1 , 0);
                if (graph.containsKey(lnode0)) {
                    // lnode0 = graph.get(lnode0);
                    // spoint.edges.add(lnode0);
                    // lnode0.edges.add(spoint);
                } else {
                    pspoint.edges.add(spoint);
                    spoint.edges.add(pspoint);
                }
            }
            int acol = (col + 1) * 2;
            Node  rpoint = new Node(arow, acol);             
            if (!graph.containsKey(rpoint)) {
                graph.put(rpoint, rpoint);
            }
            rpoint = graph.get(rpoint);
            if (arow + 1 < rows * 2) {
                Node rnode8 = new Node(arow + 1, acol);
                if (graph.containsKey(rnode8)) {
                    rnode8 = graph.get(rnode8);
                    rpoint.edges.add(rnode8);
                    rnode8.edges.add(rpoint);   
                }
            }
            if (arow - 1 > 0) {
                Node lnode8 = new Node(arow -1 , acol);
                if (graph.containsKey(lnode8)) {
                    // lnode8 = graph.get(lnode8);
                    // rpoint.edges.add(lnode8);
                    // lnode8.edges.add(rpoint);
                } else {
                    prpoint.edges.add(rpoint);
                    rpoint.edges.add(prpoint);
                }
            }
            pspoint = spoint ; prpoint = rpoint;
        }
         Node pcpoint = graph.get(new Node(0, 0)), pepoint = graph.get(new Node((rows + 1) * 2, 0)); 
        for (int i = 1; i <= col + 1 ; ++i) {   
            int acol = i * 2;
            Node cpoint = new Node(0, acol);             
             if (!graph.containsKey(cpoint)) {
                graph.put(cpoint, cpoint);
            }
            cpoint = graph.get(cpoint);
            
            if (acol + 1 < acol) {
                Node clpoint0 = new Node(0, acol + 1);
                if (graph.containsKey(clpoint0)) {
                    clpoint0 = graph.get(clpoint0);
                    cpoint.edges.add(clpoint0);
                    clpoint0.edges.add(cpoint);
                } 
            }
            if (acol - 1 > 0) {
                Node crpoint0 = new Node(0, acol - 1);
                if (graph.containsKey(crpoint0)) {
                    // crpoint0 = graph.get(crpoint0);
                    // cpoint.edges.add(crpoint0);
                    // crpoint0.edges.add(cpoint);
                } else {
                    pcpoint.edges.add(cpoint);
                    cpoint.edges.add(pcpoint);
                }
            }
            int arow = (rows + 1) * 2;
            Node  epoint = new Node(arow, acol);
            if (!graph.containsKey(epoint)) {
                graph.put(epoint, epoint);
            }
            epoint = graph.get(epoint);
            
            if (acol + 1 < acol) {
                Node elpoint = new Node(arow , acol + 1);
                if (graph.containsKey(elpoint)) {
                    elpoint = graph.get(elpoint);
                    epoint.edges.add(elpoint);
                    elpoint.edges.add(epoint);
                }
            }
            if (acol - 1 > 0) {
                Node erpoint = new Node(arow, acol -1);
                if (graph.containsKey(erpoint)) {
                    // erpoint = graph.get(erpoint);
                    // epoint.edges.add(erpoint);
                    // erpoint.edges.add(epoint);
                } else {
                    pepoint.edges.add(epoint);
                    epoint.edges.add(pepoint);
                }
            }
            pcpoint = cpoint; pepoint = epoint;
                
        }
        
        int ans = 0;
        for (Map.Entry<Node, Node> entry : graph.entrySet()) {
            Node srcNode = entry.getKey();
            System.out.println(srcNode.row + ":: " + srcNode.col + "::");
            srcNode.edges.forEach(nd -> {System.out.println(nd.row + ":: " + nd.col + "::");});
            System.out.println("*****===*****");
            // Node srcNode = entry.getKey();
            if (!srcNode.visited) {
                srcNode.visited = true;
                ans+= dfs(srcNode);
                
            }
        }
            
        return ans;
       
        
    }
}
