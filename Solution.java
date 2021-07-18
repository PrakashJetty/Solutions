class Solution {
    
    
    
    class Node {
        int i;
        int j;
        int height;
        Node left;
        Node right;
        Node up;
        Node down;
        int  reached = 0;
        int attached = 0;
        int leftReached = -1;
        int rightReached = -1;
        int downReached = -1;
        int upReached = -1;
        boolean visited = false;
        
        Node(int i, int j, int height) {
            this.i = i;
            this.j = j;
            this.height = height;
        }
        @Override
        public String toString() {
            return "NODE::" + i +"::"+j +"::"+ height+  "::"  +leftReached + "::"+ rightReached +"::"+ upReached +"::"+downReached;
        }
    }
    
    public void dfs(Node start, List<List<Integer>> result) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(start);

            // if (node.attached >= 3)
            //     return node.attached;
            // if (node.reached >= 3)
            //     return 3;
            // if (leftReached > 0 && rightReached > 0 && upReached > 0 && downReached > 0)
            //     return node.reached;
            while(!queue.isEmpty()) {
                Node node = queue.poll();
                node.visited = true;
                System.out.println(node);
                // if (node.leftReached > 0 && node.rightReached > 0 && node.upReached > 0 && node.downReached > 0)
                //     continue;
                // if (node.leftReached > 0 && node.rightReached > 0 
                //     && node.upReached > 0 && node.downReached > 0)
                //     continue;
                int left = 0, right = 0, up = 0, down = 0;
                Node leftNode = node.left;
                
                if (leftNode != null && !leftNode.visited) {
                    left = leftNode.reached > 0 ? leftNode.reached : leftNode.attached;
                    // leftNode.rightReached = 1;
                    queue.add(leftNode);
                    System.out.println(leftNode);
                    
                }
                Node rightNode = node.right;
                if (rightNode != null && !rightNode.visited) {
                    right = rightNode.reached > 0 ? rightNode.reached : rightNode.attached;
//                    rightNode.leftReached = 1;
                    queue.add(rightNode);
                    System.out.println(rightNode);
                }
                Node upNode = node.up;
                if (upNode != null && !upNode.visited) {
                    up = upNode.reached > 0 ? upNode.reached : upNode.attached;
                    // upNode.downReached = 1;
                    queue.add(upNode);
                    System.out.println(upNode);
                }
                Node downNode = node.down;
                if (downNode != null && !downNode.visited ) {
                    down = downNode.reached > 0 ? downNode.reached : downNode.attached;
                    // downNode.upReached = 1;
                    queue.add(downNode);
                    System.out.println(downNode);
                }
                System.out.println(node.i +"::" + node.j + "::"+ left + "::" + right + "::" + up +"::"+down);
            
                node.reached +=  Math.max(node.attached, Math.max(up+down , Math.max(Math.max(left + right, left + down), right + up)));
                List<Integer> list = new ArrayList<>();
                if (node.reached >= 3) {
                    list.add(node.i);
                    list.add(node.j);
                    result.add(list);
                }
                


                System.out.println(node.i +"::" + node.j + "::"+ node.reached + "::" + node.attached);
            }
            
//             int left = 0, right = 0, up = 0, down = 0;
            
//             if (node.left != null) {
//                 left = node.left.reached > 0 ? node.left.reached : node.left.attached;
//                 queue.add(left);
//             }
//             if (node.right != null) {
//                right = dfs(node.right, result);
//             }
            
//             if (node.up != null) { 
//                 up = dfs(node.up, result);
//             }
//             if (node.down != null) {
//                 down = dfs(node.up, result);
//             }
        
//                 System.out.println(node.i +"::" + node.j + "::"+ left + "::" + right + "::" + up +"::"+down);
            
//             node.reached =  Math.max(node.attached, Math.max(up+down , Math.max(Math.max(left + right, left + down), right + up)));
//             List<Integer> list = new ArrayList<>();
//             if (node.reached >= 3) {
//                 list.add(node.i);
//                 list.add(node.j);
//                 result.add(list);
//             }
                
            

//         System.out.println(node.i +"::" + node.j + "::"+ node.reached + "::" + node.attached);
        //return node.reached;
        
    }
    
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        
        Node start = null;
        Map<String, Node> nmap = new HashMap<>();
        int max = 0, maxi = 0, maxj = 0, maxAdj = 0;
        
        for (int i = 0; i < m ; ++i) {
            for (int j = 0; j < n ; ++j) {
                String key = i + "-" +j;
                Node node = nmap.get(key);
                if (node == null) {
                    node = new Node(i, j, heights[i][j]);
                    if (i == 0 || j == 0)
                        node.attached = 1;
                    if (i == m - 1 || j == n - 1)
                        node.attached += 2;
                    nmap.put(key, node);
                    if (heights[i][j] > max) {
                        max = heights[i][j];
                        maxi = i;
                        maxj = j;
                    }
                }
                //System.out.println(node);
                int left = j >= 1 ? j - 1 : -1, right = j < n - 1 ? j + 1 : -1;
                int down = i < m - 1 ? i + 1 : -1, up = i >= 1 ? i - 1 : -1;
                if (left != -1 && heights[i][left] <= heights[i][j]) {
                    key = i + "-" +left;
                    Node leftNode = nmap.get(key);
                    if (leftNode == null) {
                        leftNode = new Node(i, left, heights[i][left]);
                        if (left == 0 || i == 0)
                            leftNode.attached = 1;
                        if (left == n - 1 || i == n - 1)
                            leftNode.attached += 2;
                        nmap.put(key, leftNode);
                        if (heights[i][left] > max) {
                            max = heights[i][left];
                            maxi = i;
                            maxj = left;
                        }
                    }
                    
                    node.left = leftNode;
                    //leftNode.rightReached = 0;
                    
                    //System.out.println(leftNode);
                } else if (left != -1 && heights[left][j] >= heights[i][j]) {
                    //node.rightReached = 0;
                }
                if (right != -1 && heights[right][j] <= heights[i][j]) {
                    key = right + "-" +j;
                    Node rightNode = nmap.get(key);
                    if (rightNode == null) {
                        rightNode = new Node(right, j, heights[right][j]);
                        if (right == 0 || j == 0)
                            rightNode.attached = 1;
                        if (right == m - 1 || j == n - 1)
                            rightNode.attached += 2;
                        nmap.put(key, rightNode);
                        if (heights[right][j] > max) {
                            max = heights[right][j];
                            maxi = right;
                            maxj = j;
                        }
                    }
                    node.right = rightNode;
                    // node.leftReached = 0;
                    //System.out.println(rightNode);
                } else if (right != -1 && heights[right][j] >= heights[i][j]) {
                    //node.leftReached = 0;
                }
                if (up != -1 && heights[i][up] <= heights[i][j]) {
                    key = i + "-" +up;
                    Node upNode = nmap.get(key);
                    if (upNode == null) {
                        upNode = new Node(i, up, heights[i][up]);
                        if (i == 0 || up == 0)
                            upNode.attached = 1;
                        if (right == m - 1 || up == n - 1)
                            upNode.attached += 2;
                        nmap.put(key, upNode);
                        if (heights[i][up] > max) {
                            max = heights[i][up];
                            maxi = i;
                            maxj = up;
                        }
                    }
                    node.up = upNode;

                    //System.out.println(upNode);
                } else if (up != -1 && heights[i][up] >= heights[i][j]) {
                    //node.downReached = 0;
                }
                if (down != -1 && heights[i][down] <= heights[i][j]) {
                    key = i + "-" + down;
                    Node downNode = nmap.get(key);
                    if (downNode == null) {
                        downNode = new Node(i, down, heights[i][down]);
                        if (i == 0 || down == 0)
                            downNode.attached = 1;
                        if (right == m - 1 || down == n - 1)
                            downNode.attached += 2;
                        nmap.put(key, downNode);
                        if (heights[i][down] > max) {
                            max = heights[i][down];
                            maxi = i;
                            maxj = down;
                        }
                    }
                    node.down = downNode;
                    //node.upReached = 0;
                    //System.out.println(downNode);
                } else if (down != -1 && heights[i][down] >= heights[i][j]) {
                    //node.upReached = 0;
                }
             
            }
        }
        
        List<List<Integer>> result = new ArrayList<>();
        start = nmap.get(maxi +"-"+maxj);
        nmap.clear();
        dfs(start, result);
        return result;
    }
}
