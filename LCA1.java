//https://leetcode.com/problems/max-consecutive-ones-iii/
public class LCA1 {

 //Definition for a binary tree node.
  public class TreeNode {
     int val;
    TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
  }
 
    public boolean dfs(TreeNode root, TreeNode node, Queue<TreeNode> stack) {
        if (root == null)
            return false;
        if (root.val == node.val) {
            return true;
        }
        
        boolean lfound = dfs(root.left, node, stack);
        if (lfound) {
            System.out.println(node.val +" : " + root.val);
            stack.add(root);
            return lfound;
        }
        
        boolean rfound = dfs(root.right, node, stack);
        if (rfound) {
            System.out.println(node.val +" : " + root.val);
            stack.add(root);
            return rfound;
        }
        return false;
        
    }

     public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val == p.val || root.val == q.val)
            return root;
        if ((root.left != null && root.left.val == p.val && root.right != null && root.right.val == q.val) || 
            (root.right != null && root.right.val == p.val && root.left != null && root.left.val == q.val))
            return root;
        

        Queue<TreeNode> pqueue = new LinkedList<>();
        Queue<TreeNode> qqueue = new LinkedList<>();
        Queue<TreeNode> ppqueue = new LinkedList<>();
        Queue<TreeNode> pqqueue = new LinkedList<>();
        TreeNode node = root;

        
        dfs(root, p, ppqueue);
        ppqueue.add(p);
        dfs(root, q, pqqueue);
        pqqueue.add(q);
        TreeNode pnode = null, qnode = null;
        TreeNode found = null;

        int psize = ppqueue.size();
        int qsize = pqqueue.size();

        if ( psize > qsize) {
            while(ppqueue.size() != qsize) {
                TreeNode node1 = ppqueue.poll();
               
                System.out.println(" P : " + node1.val);
                 if (node1.val == q.val) {
                    found = q;
                    break;
                }
            }
        } else if (psize < qsize) {
            while(pqqueue.size() != psize) {
                TreeNode node1 =  pqqueue.poll();
                System.out.println(" Q : " + node1.val);
                 if (node1.val == p.val) {
                    found = p;
                    break;
                }
            }
        }
         
         if (found == null) {
        
            while (!ppqueue.isEmpty() && !pqqueue.isEmpty())  {

                pnode = ppqueue.poll();
                //System.out.println(pnode.val);
                qnode = pqqueue.poll();
                System.out.println(pnode.val +" : " + qnode.val);
                //System.out.println(qnode.val);
                if (pnode.val == qnode.val) {
                    found = pnode;
                    break;
                }

            }
         }
        
        if (found != null)
            return found;
        else
            return root;
    }
}
