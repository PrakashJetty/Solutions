
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

public class Sol10 {

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
        if (node == null)
            return null;
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

    static void frameChildren(Node n, Map<Integer, Node> nMap) {
        for(Edge ce : n.edges) {
            Node cn = ce.out;
            n.children.add(cn);
            cn.parent = n;
            nMap.remove(cn.index);
            if (cn.edges.size() > 0) {
                frameChildren(cn, nMap);
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
            Node dlnode = null, drnode = null;
            if (index > 1) {
                dlnode = dfs(new Node(edge[0], null), root);
                drnode = dfs(new Node(edge[1], null), root);
            }
            if (dlnode != null) {

                if (crNode != null) {
                    dlnode.children.add(crNode);
                    crNode.parent = dlnode;
                    nMap.remove(crNode.index);
                    frameChildren(crNode, nMap);
                } else {
                    crNode = new Node(edge[1], dlnode);
                    dlnode.children.add(crNode);
                }
                } else if (drnode != null) {
                    if (clNode != null) {
                        drnode.children.add(clNode);
                        clNode.parent = drnode;
                        nMap.remove(clNode.index);
                        frameChildren(clNode, nMap);
                    } else {
                        clNode = new Node(edge[0], drnode);
                        drnode.children.add(clNode);
                    }
            } else {
                    if (index == 1) {
                        clNode = new Node(edge[0], null);
//                        nMap.put(edge[0], clNode);
                        crNode =  new Node(edge[1], clNode);
//                        nMap.put(edge[1], crNode);
                        // Edge e = new Edge(clNode, crNode);
                        clNode.children.add(crNode);  
                        root = clNode;  
                    } else {
                        if (clNode != null) {
                            crNode = new Node(edge[1], null);
                            nMap.put(edge[1], crNode);
                            Edge e = new Edge(clNode, crNode);
                            clNode.edges.add(e);
                        } else if (crNode != null) {
                            clNode = new Node(edge[0], null);
                            nMap.put(edge[0], clNode);
                            Edge e = new Edge(crNode, clNode);
                            crNode.edges.add(e);
                        } else {
                            clNode = new Node(edge[0], null);
                            nMap.put(edge[0], clNode);
                            crNode = new Node(edge[1], null);
                            nMap.put(edge[1], crNode);
                            Edge e = new Edge(clNode, crNode);
                            clNode.edges.add(e);
                        }
                    }
                    
                               
            }
            
            ++index;        
        }
        for(Map.Entry<Integer, Node> entry: nMap.entrySet()) {
            Node cnode = entry.getValue(), dnode = null;
            for(Edge e : cnode.edges) {
                Node enode = e.out;
                dnode = dfs(enode, root);
                if (dnode != null) {
                    dnode.children.add(cnode);
                    cnode.parent = dnode;
                    e.cut = true;
                    break;
                }
            }
            for(Edge e : cnode.edges) {
                if (!e.cut) {
                    Node enode = e.out;
                    cnode.children.add(enode);
                    enode.parent = cnode;
                }
            }
            cnode.edges.clear();

        }
        nMap.clear();
        nMap = null;
    }

    static Node moveToLeafNode(Node n) {
        if (n.children.size() == 0)
            return n;
        Optional<Node>  ocn = n.children.stream().filter(nc -> !nc.visited).findFirst();
        return ocn.map(Sol8::moveToLeafNode).orElse(null);
    }

    // using BFS calculate nodes at each level 
    static int bfs(int k, Node root) {
        int level = 1, count = 0;
        Queue<Node> lnodes = new LinkedList<>();
        Queue<Node> nnodes = root.children.stream().filter(c -> c.cutVisit).collect(Collectors.toCollection(LinkedList::new));
        while(level <= k - 1 && !nnodes.isEmpty()) {
            if (level == 1) {
                count += (Math.pow(2, nnodes.size()) - 1 );
            } else {
                count += (Math.pow(2, nnodes.size()) - 1 ) * (level);
            }
            while(!nnodes.isEmpty()) {
                Node nn = nnodes.remove();
                lnodes.addAll(nn.children.stream().filter(c -> c.cutVisit).collect(Collectors.toList()));
            }

            ++level;    
            nnodes.addAll(lnodes);
            lnodes.clear();
        }        
        return count;
    }

    static void calculateNodesAndSubTrees(Node root, int totalNodes, int k) {
        int chindex = 0;
        
        Node lfnode = moveToLeafNode(root);
        while(lfnode != null) {
            Queue<Node> bnodes = new LinkedList<>();
            lfnode.cnodes = 0;
            count[lfnode.index - 1] = 1;
            bnodes.add(lfnode);
            Node cnode = lfnode.parent, prevnode = lfnode;
            int index = 0;
            while(!cnode.equals(root)) {
                cnode.cnodes = prevnode.cnodes + 1;
                count[cnode.index - 1] = Math.min(cnode.cnodes + 1, totalNodes - k);
                prevnode = cnode;
                bnodes.add(cnode);
                cnode = cnode.parent;
                ++index;
            }
            if (chindex > 0) {
                int dindex = 1;
                Queue<Node> nodestobeset = new LinkedList<>();
                while(!bnodes.isEmpty()) {
                    Node dnode = bnodes.remove();
                    nodestobeset.add(dnode);
                    if (dindex <= totalNodes - k) {
                        count[dnode.index -1] += bfs((totalNodes - k) - dindex, root);
                    }
                    ++dindex;
                }
                nodestobeset.forEach(bn -> {
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
       BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

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
        cn = new int[n][n - 1][n-k];
        count = new int[n];
        makeTree(edges);
        int result = cutTree(n, k) + 2;
        System.out.println(result);

       bufferedWriter.write(String.valueOf(result));
       bufferedWriter.newLine();

       bufferedWriter.close();
    }
}
