
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
        List<Edge> edges;
        boolean visited;
        boolean cutVisit;
        Map<Integer, Integer> cnMap = new HashMap<>();

        public Node(final int index) {
            this.index = index;
            this.edges = new ArrayList<>();
            visited = false;
            cutVisit = false;
        }

        public Node(final int degree, final int index) {
            this.degree = degree;
            this.index = index;
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
    static Map<Integer, Node> nMap = new HashMap<>();
    static SortedMap<Integer, List<Node>> sMap = new TreeMap<>();
    static int[][][] cn ;
    static int count[];

    static void makeDegrees(int[][] edges) {
        for (int[] edge : edges) {
            Node l = new Node(edge[0]);
            Node r =  new Node(edge[1]);
            nMap.putIfAbsent(edge[0], l);
            Node clNode = nMap.get(edge[0]);
            Edge edgel = new Edge(l, r, clNode.edges.size());
            clNode.edges.add(edgel);
            nMap.putIfAbsent(edge[1], r);
            Node crNode = nMap.get(edge[1]);

            Edge edger = new Edge(r, l, crNode.edges.size());

            crNode.edges.add(edger);


        }
    }



    static Node moveDown(Node n, Edge e) {
        if (n.edges.size() > 1) {
            Edge nne = n.edges.stream().filter(ne -> !ne.cut).findFirst().get();
            return moveDown(nne.out, nne);
        }
        else
            return n;
    }

    static Edge moveUp(Node n) {
        return n.edges.stream().filter(e -> !e.cut).findFirst().get();
    }

    static Node moveToLeafNode(Node n, Edge e) {
        if (n.edges.size() == 0)
            return n;
        Edge ne = n.edges.get(0);
        return moveToLeafNode(ne.out, ne);
    }

    static void combinationUtil(List<int[]> combinations, int data[], int start, int end, int index)
    {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            combinationUtil(combinations, data, start + 1, end, index + 1);
            combinationUtil(combinations, data, start + 1, end, index);
        }

    }

    static Map<Integer, Integer> bfs(int[] combs, int k, Node n) {
        Queue<Node> nnodes = new LinkedList<>();
        int level = 1;
        Map<Integer, Integer> lcount = new HashMap<>();
        nnodes.add(n);
        List<Edge> edges = n.edges;
        lcount.put(level, edges.size());
        Queue<Node> lnodes = new LinkedList<>();
        for(int i = 1; i < combs.length / k; ++i) {
            while(nnodes.isEmpty()) {
                Node nn = nnodes.remove();
                for (Edge e : nn.edges) {
                    lnodes.add(e.out);
                }
                ++level;
                lcount.put(level, lnodes.size());
            }
            nnodes.addAll(lnodes);
            lnodes.clear();
        }
        return lcount;
    }

    static int calculateSubtrees(int[] combs, int k, Node n) {
        Map<Integer, Integer>  lcount =  bfs(combs, k, n);
        int count = 0, level = 1;
        for(Map.Entry<Integer, Integer> entry : lcount.entrySet()) {
            if (level == 1) {
                ++ count;
                continue;
            } else {
                count += (Math.pow(2, entry.getValue()) - 1 ) * (level - 1);
            }
        }
        return count;
    }

    static void calculateNodesAndSubTrees(Node n, Node pn, Edge e, int totalNodes, int k) {
        e.cut = true;
        if (pn == null) {
           Edge ne = moveUp(n);
           ne.cut = true;

           calculateNodesAndSubTrees(ne.out, n, ne, totalNodes, k);
        } else {
            if (n.edges.size() == 1) {
                int pcn = pn.cnMap.values().stream().mapToInt(i -> i.intValue()).sum();
                n.cnMap.put(e.index, pcn + 1);
                count[n.index] = pcn + 1;
            } else if (n.edges.size() > 1) {
                // for each of remaining edges
                    // go down the path to leaf
                    // calculate 
                    // moveup
                    // calculate
                    // repaet until reaches original node
                    // propogate possible subtrees to each of node in current edge path with other alredy travesed paths
                        // calculate possible combination of edges from root node
                        // let e1, e2, ...en, e1e2, e1e3, ... e1e2e3..en  be those comninations 
                        // with cn1, cn2...cn , cn1cn2 are connected nodes from root node correspondinlgy on those edges
                        // total subtrees would be dp solution as 
                        // its recursive and previously calculated combinations can be used
                int ecount = 0;
                int ceindex = 0;
                for(Edge ne: n.edges) {
                    Node nn = moveToLeafNode(n, ne);
                    Edge nne = nn.edges.get(0);
                    nn.cnMap.put(nne.index, 1);
                    count[nn.index] = 1;
                    ecount += 1;
                    nne.cut = true;
                    while(nne.out.equals(n)) {
                        Node un = nne.out;
                        Edge ue = un.edges.get(0);
                        if (un.edges.size() == 1) {

                            un.cnMap.put(ue.index, nn.cnMap.get(nne.index) + 1);
                            ecount += count[un.index] = count[nn.index] + 1;
                            nne = ue;
                        } else {
                            calculateNodesAndSubTrees(un, nn, ue, totalNodes, k);
                        }
                    }
                    n.cnMap.put(e.index, ecount);                
                    ecount = 0;
                    ++ceindex;
                    if (ceindex > 1) {
                        Map<Integer, List<int[]>> combMap = new HashMap<>();
                        int dindex = 1;
                        while(nne.out != null) {
                            Node dn = nne.out;
                            if (dindex == 1) {
                                Set<Map.Entry<Integer, Integer>> entrySet = n.cnMap.entrySet();
                                for (int i = 0; i < entrySet.size() - 1; ++i) {
                                    int[] iarray = entrySet.stream()
                                    .filter(ed -> !ed.getKey().equals(e.index))
                                    .mapToInt(ed -> ed.getValue().intValue()).toArray();
                                    List<int[]> combNodes = new ArrayList<>();
                                    combinationUtil(combNodes, iarray, 0, iarray.length - 1, i);
                                    combMap.put(i, combNodes);
                                } 
                                for(Map.Entry<Integer, List<int[]>> entry: combMap.entrySet()) {
                                    if (dindex >= totalNodes - k) {
                                        List<int[]> cccombs = entry.getValue();
                                        cccombs.forEach( combc -> {
                                            count[dn.index] += calculateSubtrees(combc, (totalNodes - k) - dindex, n);
                                        });

                                    }
                                }
                            }
                        }
                        
                    }

                }


            }
        }
    }

    /*
     * Complete the cuttree function below.
     */
    static int cutTree(int totalNodes, int k) {
        /*
         * Write your code here.
         */
        Node cnode = nMap.get(1);
        Node n = moveDown(cnode, cnode.edges.get(0));
        cn[n.index][n.edges.get(0).index][(totalNodes - k)] = 1;
        count[n.index] = 1;
        calculateNodesAndSubTrees(n, null, n.edges.get(0), totalNodes, k);

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
                edges[edgesRowItr][edgesColumnItr] = edgesItem - 1;
            }
        }
//        degrees = new int[n];
        cn = new int[n][n - 1][n-k];
        count = new int[n];
        // for(int i = 0; i < n ; ++i) {
        //     Arrays.fill(dp[i], 0);
        // }

        makeDegrees(edges);
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
