/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Leetcode337 {
    
  
    Map<TreeNode, Integer> dfsmap = new HashMap<>();
   
    public int dfsRob(TreeNode root) {
        if (root == null)
            return 0;
        if (dfsmap.containsKey(root))
            return dfsmap.get(root);
        TreeNode left = root.left, right = root.right, 
        leftleft = null, leftright = null, 
        rightleft = null, rightright = null;
        
        int leftvalue = 0, rightvalue = 0,
        leftleftvalue = 0, leftrightvalue = 0,
        rightleftvalue = 0, rightrightvalue = 0;
        
        if (left !=null) {
            // leftvalue = dfsmap.containsKey(left.val) ? dfsmap.get(left.val) : leftvalue;
            leftleft = left.left;
            // leftleftvalue = leftleft != null 
            //     && dfsmap.containsKey(leftleft.val) ? dfsmap.get(leftleft.val) : leftleftvalue;
            leftright = left.right;
            // leftrightvalue = leftright != null 
            //     && dfsmap.containsKey(leftright.val) ? dfsmap.get(leftright.val) : leftrightvalue;
        }
        if (right != null) {
            // rightvalue = dfsmap.containsKey(right.val) ? dfsmap.get(right.val) : rightvalue;
            rightleft = right.left;
            // rightleftvalue = rightleft != null 
            //     && dfsmap.containsKey(rightleft.val) ? dfsmap.get(rightleft.val) : rightleftvalue;
            rightright = right.right;
            // rightrightvalue = rightright != null 
            //      && dfsmap.containsKey(rightright.val) ? dfsmap.get(rightright.val) : rightrightvalue;
        }
        
        int max =  Math.max(root.val + dfsRob(leftleft) + dfsRob(leftright) + dfsRob(rightleft) +  dfsRob(rightright),  dfsRob(root.left) +  dfsRob(root.right));
        dfsmap.put(root, max);
        return max;
    }
    public int rob(TreeNode root) {        
        if (root.left == null && root.right == null)
            return root.val;
        return dfsRob(root);
        // dfsmap.entrySet().stream().forEach(e -> System.out.println(e.getKey() +  "::" + e.getValue()));
        // //return 
        // return dfsmap.get(root.val);
    }
}


// 

 // - - - - - - 
 // i - 4  i - 3  i - 2    i

// dfs(root)
    //if node in next level below has to be taken 
