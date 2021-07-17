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
        
        Node(int i, int j, int height) {
            this.i = i;
            this.j = j;
            this.height = height;
        }
    }
    
    public Node dfs(Node node, List<List<Integer>> result) {
        
      

            int left = 0, right = 0, up = 0, down = 0;
            
            if (node.left != null) {
                Node leftNode = dfs(node.left, result);
                if (leftNode.reached >= 3) {
                    left = 3;
                } else {
                    left = Math.max(leftNode.attached, leftNode.reached);
                }
            }
            else
                left = 0;
            if (node.right != null) {
               Node rightNode = dfs(node.right, result);
                if (rightNode.reached >= 3) {
                    right = 3;
                } else {
                    right = Math.max(rightNode.attached, rightNode.reached);
                }
            }
            else
                right = 0;
            if (node.up != null) { 
               Node upNode = dfs(node.up, result);
                if (upNode.reached >= 3) {
                    up = 3;
                } else {
                    up = Math.max(upNode.attached, upNode.reached);
                }
            }
            else
                up = 0;
            if (node.down != null) {
                Node downNode = dfs(node.up, result);
                if (downNode.reached >= 3) {
                    down = 3;
                } else {
                    down = Math.max(downNode.attached, downNode.reached);
                }
            }
            else
                down = 0;
            
            node.reached =  Math.max(node.attached, Math.max(up+down , Math.max(Math.max(left + right, left + down), right + up)));
            List<Integer> list = new ArrayList<>();
            if (node.reached >= 3) {
                list.add(node.i);
                list.add(node.j);
                result.add(list);
            }
                
            
        //}
        System.out.println(node.i +"::" + node.j + "::"+ node.reached + "::" + node.attached);
        return node.reached;
        
    }
    
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        
        Node start = null;
        Map<String, Node> nmap = new HashMap<>();
        
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
                }
                int left = i > 1 ? i - 1 : 0, right = i < m - 1 ? i + 1 : m;
                int down = j < n - 1 ? j + 1 : n , up = j > 1 ? j - 1 : 0;
                if (heights[left][j] <= heights[i][j]) {
                    key = left + "-" +j;
                    Node leftNode = nmap.get(key);
                    if (leftNode == null) {
                        leftNode = new Node(left, j, heights[left][j]);
                        if (left == 0 || j == 0)
                            leftNode.attached = 1;
                        if (left == m - 1 || j == n - 1)
                            leftNode.attached += 2;
                        nmap.put(key, leftNode);
                    }
                    
                    node.left = leftNode;
                }
                if (heights[right][j] <= heights[i][j]) {
                    key = right + "-" +j;
                    Node rightNode = nmap.get();
                    if (rightNode == null) {
                        rightNode = new Node(right, j, heights[right][j]);
                        if (right == 0 || j == 0)
                            rightNode.attached = 1;
                        if (right == m - 1 || j == n - 1)
                            rightNode.attached += 2;
                        nmap.put(key, rightNode);
                    }
                    node.right = rightNode;
                }
                if (heights[i][up] <= heights[i][j]) {
                    key = i + "-" +up;
                    Node upNode = nmap.get(key);
                    if (upNode == null) {
                        upNode = new Node(i, up, heights[up][j]);
                        if (i == 0 || up == 0)
                            upNode.attached = 1;
                        if (right == m - 1 || up == n - 1)
                            upNode.attached += 2;
                        nmap.put(key, upNode);
                    }
                    node.up = upNode;
                }
                if (heights[i][down] <= heights[i][j]) {
                    key = i + "-" + down;
                    Node downNode = nmap.get(key);
                    if (downNode == null) {
                        downNode = new Node(i, down, heights[down][j]);
                        if (i == 0 || down == 0)
                            downNode.attached = 1;
                        if (right == m - 1 || down == n - 1)
                            downNode.attached += 2;
                        nmap.put(key, downNode);
                    }
                    node.down = downNode;
                }
             if (i == 0 && j == 0)
                 start = node;
            }
        }
        
        List<List<Integer>> result = new ArrayList<>();
        dfs(start, result);
        return result;
    }
}
