
import java.io.*;
import java.math.*;
import java.nio.file.NotDirectoryException;
import java.text.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sol8 {

    static class Edge {
        Node in;
        Node out;
        int cnodes = 0;
        boolean cut = false;
        int  index;

        public Edge(final Node in, final Node out) {
            this.in = in;
            this.out = out;
        }
        public Edge(final Node in, final Node out, int index) {
            this.in = in;
            this.out = out;
            this.index = index;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge e = (Edge) o;
            return index == e.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }
    }

    static class Node {
        int degree;
        int index;
        List<Node> children;
        List<Edge> edges;
        Node parent;
        boolean visited;
        boolean cutVisit;
        int cnodes;

        public Node(final int index, Node parent) {
            this.index = index;
            this.parent = parent;
            visited = false;
            cutVisit = false;
            this.children = new ArrayList<>();
            this.edges = new ArrayList<>();
        }


        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return index == node.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }
    }

    //    static int[] degrees ;
    static Node root;
    static SortedMap<Integer, List<Node>> sMap = new TreeMap<>();
    static int[][][] cn ;
    static int count[];

    static Node dfs(Node node, Node root) {
        if (root.equals(node))
            return root;
        Node found = null;
        for (Node nn : root.children) {
            found = dfs(node, nn);
            if (found != null)
                break;
        }


        return found;
    }

    static void frameChildren(Node n) {
        for(Edge ce : n.edges) {
            Node cn = ce.out;
            n.children.add(cn);
            if (cn.edges.size() > 0) {
                frameChildren(cn);
            }
        }
        n.edges.clear();
    }

    static void makeTree(int[][] edges) {
        Map<Integer, Node> nMap = new HashMap<>();

        int index = 1;
        for (int[] edge : edges) {
            Node clNode = nMap.get(edge[0]);
            Node crNode = nMap.get(edge[1]);
            if (clNode != null && crNode != null) {
                Node dlnode = dfs(clNode, root);
                Node drnode = dfs(crNode, root);
                if (dlnode != null) {
//                    if (crNode.parent != null && crNode.parent.equals(clNode)) {
                    // Node parent = crNode.parent;
                    // if (parent != null) {
                    //     parent.parent = crNode;
                    //     parent.children = new ArrayList<>();
                    //     crNode.children.add(parent);
                    // }
                        dlnode.children.add(crNode);
                        crNode.parent = dlnode;
                        frameChildren(crNode);
//                    } else {
//                        dlnode.children=crNode;
//                    }
                } else if (drnode != null) {
//                    if (clNode.parent != null && clNode.parent.equals(crNode)) {
                        // Node parent = clNode.parent;
                        // if (parent != null) {
                        //     parent.parent = clNode;
                        //     parent.children = new ArrayList<>();
                        //     clNode.children.add(parent);
                        // }
                        drnode.children.add(clNode);
                        clNode.parent = drnode;
                        frameChildren(clNode);
//                    } else {
//                        drnode.parent = clNode;
//                    }
                } else {
                    Edge e = new Edge(clNode, crNode);
                    clNode.edges.add(e);
                }
            } else if (clNode != null) {
                Node dlnode = dfs(clNode, root);
                if (dlnode != null) {
                    crNode =  new Node(edge[1], dlnode);
                    nMap.put(edge[1], crNode);
                    dlnode.children.add(crNode);
//                    frameChildren(crNode);
                } else{
                    crNode =  new Node(edge[1], null);
                    nMap.put(edge[1], crNode);
                    Edge e = new Edge(clNode, crNode);
                    clNode.edges.add(e);
                }
                
            } else if (crNode != null) {
                Node drnode = dfs(crNode, root);
                 if (drnode != null) {
                     clNode = new Node(edge[0], drnode);
                     nMap.put(edge[0], clNode);
                    drnode.children.add(clNode);
//                    frameChildren(clNode);
                } else {
                    clNode = new Node(edge[0], null);
                    nMap.put(edge[0], clNode);
                    Edge e = new Edge(crNode, clNode);
                    crNode.edges.add(e);
                }
                    
            }  else {
                    if (index == 1) {
                        clNode = new Node(edge[0], null);
                        nMap.put(edge[0], clNode);
                        crNode =  new Node(edge[1], clNode);
                        nMap.put(edge[1], crNode);
                        // Edge e = new Edge(clNode, crNode);
                        clNode.children.add(crNode);  
                        root = clNode;  
                    } else {
                        clNode = new Node(edge[0], null);
                        nMap.put(edge[0], clNode);
                        crNode =  new Node(edge[1], null);
                        nMap.put(edge[1], crNode);
                        Edge e = new Edge(clNode, crNode);
                        clNode.edges.add(e);  
                    }
                    
                               
            }
            
            ++index;        
        }
//        for (Map.Entry<Integer, Node> entry: nMap.entrySet()) {
//            Node cn = entry.getValue();
//            if (dfs(cn, root) == null) {
//                Node parent = dfs(cn.parent, root);
//                if (parent != null) {
//                    parent.children.add(cn);
//
//                }
//            }
//        }
        nMap.clear();
        nMap = null;
    }



//    static Node moveDown(Node n, Edge e) {
//        if (n.edges.size() > 1) {
//            Edge nne = n.edges.stream().filter(ne -> !ne.cut).findFirst().get();
//            return moveDown(nne.out, nne);
//        }
//        else
//            return n;
//    }
//
//    static Edge moveUp(Node n) {
//        return n.edges.stream().filter(e -> !e.cut).findFirst().get();
//    }

    static Node moveToLeafNode(Node n) {
        if (n.children.size() == 0)
            return n;
        Optional<Node>  ocn = n.children.stream().filter(nc -> !nc.visited).findFirst();
        return ocn.map(Sol8::moveToLeafNode).orElse(null);
    }

    // static int[] getSubset(int[] input, int[] subset) {
    //     int[] result = new int[subset.length];
    //     for (int i = 0; i < subset.length; i++)
    //         result[i] = input[subset[i]];
    //     return result;
    // }
    // static List<int[]> combinationUtil(int[] input, int k) {
    //     int[] s = new int[k];
    //     List<int[]> subsets = new ArrayList<>();

    //     if (k <= input.length) {
    //         // first index sequence: 0, 1, 2, ...
    //         for (int i = 0; (s[i] = i) < k - 1; i++) ;
    //         subsets.add(getSubset(input, s));
    //         for (; ; ) {
    //             int i;
    //             // find position of item that can be incremented
    //             for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--) ;
    //             if (i < 0) {
    //                 break;
    //             }
    //             s[i]++;                    // increment this item
    //             for (++i; i < k; i++) {    // fill up remaining items
    //                 s[i] = s[i - 1] + 1;
    //             }
    //             subsets.add(getSubset(input, s));
    //         }
    //     }
    //     return subsets;
    // }

    // using BFS calculate nodes at each level 
    static int bfs(int k, Node root) {
        int level = 1, count = 0;
        Queue<Node> lnodes = new LinkedList<>();
        Queue<Node> nnodes = root.children.stream().filter(c -> c.cutVisit).collect(Collectors.toCollection(LinkedList::new));
        while(level <= k - 1 && !nnodes.isEmpty()) {
            if (level == 1) {
                count+= (Math.pow(2, nnodes.size()) - 1 );
            } else {
                count += (Math.pow(2, nnodes.size()) - 1 ) * (level - 1);
            }
            while(!nnodes.isEmpty()) {
                Node nn = nnodes.remove();
                lnodes.addAll(nn.children.stream().filter(c -> c.visited).collect(Collectors.toList()));
            }

            ++level;    
            nnodes.addAll(lnodes);
            lnodes.clear();
        }        
        return count;
    }

    // calculate subtrees count using formula (lc1 c 1 + lc1 c 2+ .. + lc1 c lc1) * (level -1) == 2^n - 1 * (level -1)
//    static int calculateSubtrees(int[] combs, int k, List<Node> inodes) {
//        Map<Integer, Integer>  lcount =  bfs(combs, k, inodes);
//        int count = 0, level = 1;
//        for(Map.Entry<Integer, Integer> entry : lcount.entrySet()) {
//            if (level == 1) {
//                ++ count;
//            } else {
//                count += (Math.pow(2, entry.getValue()) - 1 ) * (level - 1);
//            }
//            ++level;
//        }
//        return count;
//    }

    // static void calculateNodesAndSubTrees(Node n, Node pn, int totalNodes, int k) {
    //     if (pn == null) {
    //        n.cnodes = 0;
    //        count[n.index - 1] =  1;
    //        calculateNodesAndSubTrees(n.parent, n, totalNodes, k);
    //     } else {
    //         if (n.children.size() == 1) {
    //             int pcn = n.children.get(0).cnodes + 1;
    //             n.cnodes = pcn;
    //             count[n.index -1] = pcn + 1; // TO DO ##############################
    //         } else if (n.children.size() > 1) {
    //             // for each of remaining edges
    //                 // go down the path to leaf
    //                 // calculate 
    //                 // moveup
    //                 // calculate
    //                 // repaet until reaches original node
    //                 // propogate possible subtrees to each of node in current edge path with other alredy travesed paths
    //                     // calculate possible combination of edges from root node
    //                     // let e1, e2, ...en, e1e2, e1e3, ... e1e2e3..en  be those comninations 
    //                     // with cn1, cn2...cn , cn1cn2 are connected nodes from root node correspondinlgy on those edges
    //                     // total subtrees would be dp solution as 
    //                     // its recursive and previously calculated combinations can be used
    //             int ecount = 0;
    //             int ceindex = 0;
    //             count[n.index -1] = 1;
    //             List<Node> vchildren = new ArrayList<>();
    //             List<Integer> vindexes = new ArrayList<>();
    //             for(Node cn: n.children) {

    //                 // if cn has multiple children 
    //                 if (cn.children.size() > 1) { 
    //                     calculateNodesAndSubTrees(cn, n, totalNodes, k);
    //                     ecount += count[cn.index - 1];
    //                 } else {
                    
    //                     Node nn = moveToLeafNode(cn);
    //                     // Edge nne = nn.edges.stream().filter(cenode -> cenode.index == 0).findFirst().get();
    //                     nn.cnodes = 0;
    //                     count[nn.index -1] = 1;
    //                     ecount += 1;
    //                     pn = nn;
    //                     Queue<Node> bnodes = new LinkedList<>();
    //                     bnodes.add(nn);
    //                     while(!nn.parent.equals(n)) {
    //                         Node un = nn.parent;
    //                             // Edge ue = un.edges.stream().filter(cenode -> cenode.index == 0).findFirst().get();
    //                             if (un.children.size() == 1) {
    //                                 un.cnodes = pn.cnodes + 1;
    //                                 ecount += count[un.index - 1] = count[nn.index - 1] + 1;
    // //                            nne = ue;
    //                             } else {
    //                                 calculateNodesAndSubTrees(un, pn, totalNodes, k);
    //                                 ecount += count[un.index - 1];
    //                             }
    //                             pn = un;
    //                             nn = un;
    //                             bnodes.add(nn);
    //                     }
    //                     ++ceindex;
    //                     if (ceindex > 1) {
    //                         Map<Integer, List<int[]>> combMap = new HashMap<>();
    //                         // Queue<Node> cchildren = new LinkedList<>();
    //                         int dindex = 1;
                            
    //                         // cchildren.add(nn);
    //                         while(!bnodes.isEmpty()) {
    //                             Node dn = bnodes.remove();
    //                             // if (dindex == 1) {                                
    //                             //     for (int i = 1; i < vchildren.size(); ++i) {
    //                             //         List<int[]> combNodes = combinationUtil(vindexes.stream().mapToInt(v -> v.intValue()).toArray(), i);
    //                             //         combMap.put(i, combNodes);
    //                             //     } 
    //                             // }
    //                             // for(Map.Entry<Integer, List<int[]>> entry: combMap.entrySet()) {
    //                                 if (dindex <= totalNodes - k) {
    //                                     // List<int[]> cccombs = entry.getValue();
    //                                     // for(int[] combc : cccombs)  {
    //                                         count[dn.index -1] += bfs((totalNodes - k) - dindex, vchildren);
    //                                     // };

    //                                 }
    //                             //}
    //                             // cchildren.addAll(dn.children);
    //                             ++dindex;
    //                         }
                            
    //                     }
    //                 }
    //                 cn.visited = true;
    //                 vchildren.add(cn);
    //                 vindexes.add(cn.index);
    //             }
    //         }
    //     }
    // }

    static void calculateNodesAndSubTrees(Node root, int totalNodes, int k) {
        int chindex = 0;
//        List<Node> vchildren = new ArrayList<>();

        // for(Node chnode : root.children) {

        
        Node lfnode = moveToLeafNode(root);
        while(lfnode != null) {
            Queue<Node> bnodes = new LinkedList<>();
            lfnode.cnodes = 0;
            count[lfnode.index - 1] = 1;
            bnodes.add(lfnode);
            Node cnode = lfnode.parent, prevnode = lfnode;
            int index = 0;
            while(!cnode.equals(root)) {
                
                // if (cnode.children.size() == 1) {
                    cnode.cnodes = prevnode.cnodes + 1;
                    count[cnode.index - 1] = Math.min(cnode.cnodes + 1, totalNodes - k);
                    prevnode = cnode;
                    bnodes.add(cnode);
                    cnode = cnode.parent;

                // } else {
                //     if (cnode.cutVisit) {
                //         cnode.cnodes = prevnode.cnodes + 1;
                //         count[cnode.index - 1] += Math.min(cnode.cnodes + 1, totalNodes - k);
                //         prevnode = cnode;
                //         bnodes.add(cnode);
                //         cnode = cnode.parent;
                //         cnode.cutVisit = false;
                //     } else {
                //         Node clfnode = moveToLeafNode(cnode);
                //         cnode.cutVisit = true;
                //         clfnode.cnodes = 0;
                //         count[clfnode.index - 1] = 1;
                //         prevnode = clfnode;
                //         bnodes.add(clfnode);
                //         cnode = clfnode.parent;
                //     }
                // }
                // }
                ++index;
            }
            if (chindex > 0) {
                int dindex = 1;
                for(int i = 1 ; i <= bnodes.size(); ++i) {
                    Node dnode = bnodes.peek();
                    if (dindex <= totalNodes - k) {
                        count[dnode.index -1] += bfs((totalNodes - k) - dindex, root);
                    }
//                    vchildren.add(dnode);

                    ++dindex;
                }
                bnodes.forEach(bn -> {
                    if (bn.children.size() > 0 && bn.children.stream().allMatch(bc -> bc.visited)) {
                        bn.visited = true;
                        bn.cutVisit = true;
                    } else if (bn.children.size() > 0 && !bn.children.stream().allMatch(bc -> bc.visited)) {
                        bn.cutVisit = true;
                    } else if (bn.children.size() == 0) {
                        bn.visited = true;
                        bn.cutVisit = true;

                    }
                });
            } else {
//                vchildren.add(prevnode);
                prevnode.visited = true;
                prevnode.cutVisit = true;
            }
            ++chindex;
            count[root.index -1] += prevnode.cnodes + 1;
            lfnode = moveToLeafNode(root);
        }
        // }

    }

    /*
     * Complete the cuttree function below.
     */
    static int cutTree(int totalNodes, int k) {
        /*
         * Write your code here.
         */
        calculateNodesAndSubTrees(root, totalNodes, k);

        // Calculate connected nodes for all nodes
        
        // calculate for each
        return Arrays.stream(count).sum();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0].trim());

        int k = Integer.parseInt(nk[1].trim());

        int[][] edges = new int[n-1][2];


        for (int edgesRowItr = 0; edgesRowItr < n-1; edgesRowItr++) {
            String[] edgesRowItems = scanner.nextLine().split(" ");

            for (int edgesColumnItr = 0; edgesColumnItr < 2; edgesColumnItr++) {
                int edgesItem = Integer.parseInt(edgesRowItems[edgesColumnItr].trim());
                edges[edgesRowItr][edgesColumnItr] = edgesItem;
            }
        }
//        degrees = new int[n];
        cn = new int[n][n - 1][n-k];
        count = new int[n];
        // for(int i = 0; i < n ; ++i) {
        //     Arrays.fill(dp[i], 0);
        // }

        makeTree(edges);
//        for (Map.Entry<Integer, Node> entry: nMap.entrySet()) {
//            int degree = entry.getValue().edges.size();
//            List<Node> nodes = sMap.computeIfAbsent(degree, k1 -> new ArrayList<>());
//            nodes.add(entry.getValue());
//        }

        int result = cutTree(n, k) + 2;
        System.out.println(result);

//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
    }
}
