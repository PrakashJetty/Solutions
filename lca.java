public class LCA {

 //Definition for a binary tree node.
  public class TreeNode {
     int val;
    TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
  }
 


	 public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val == p.val || root.val == q.val)
            return root;
        if ((root.left.val == p.val && root.right.val == q.val) || 
            (root.right.val == p.val && root.left.val == q.val))
	 		return root;
        

	 	Queue<TreeNode> pqueue = new LinkedList<>();
		Queue<TreeNode> qqueue = new LinkedList<>();
        Stack<TreeNode> ppqueue = new Stack<>();
		Stack<TreeNode> pqqueue = new Stack<>();
		TreeNode node = root;
        ppqueue.push(root);
	 	while(node.val != p.val && ((node.left != null && node.left.val != p.val)
             && (node.right != null && node.right.val != p.val)) {
            if (node.left != null) {
                pqueue.add(node.left);
                ppqueue.push(node.left);
               
            }
	 		if (node.right != null) {
                pqueue.add(node.right);
                ppqueue.push(node.right);
               
            }
            
			
			node = pqueue.poll();
	 	}
        ppqueue.push(node);
        pqqueue.push(root);
	 	node = root;
	 	while(node.val != q.val && (node.left != null && node.left.val != q.val)
             && (node.right != null && node.right.val != q.val)) {
            if (node.left != null) {
	 		    qqueue.add(node.left);
                pqqueue.push(node.left);
                 System.out.println(node.left.val);
            }
            if (node.right != null) {
    			qqueue.add(node.right);

			    pqqueue.push(node.right);
                 System.out.println(node.right.val);
            }
			node = qqueue.poll();
	 	}
        pqqueue.push(node);
         
	 	TreeNode pnode = null, qnode = null;
	 	TreeNode found = null;
        
	 	while (!ppqueue.isEmpty() && !pqqueue.isEmpty())  {
			pnode = ppqueue.pop();
            //System.out.println(pnode.val);
			qnode = pqqueue.pop();
            //System.out.println(qnode.val);
			if (pnode.val == qnode.val) {
				found = pnode;
				break;
			}

		}
        if (ppqueue.isEmpty()) {
            while(!pqqueue.isEmpty()) {
                qnode = pqqueue.pop();
                if (p.val == qnode.val) {
                    found = p;
                    break;
                }
            } 
        }
         if (qnode == null) {
            while(!ppqueue.isEmpty() ) {
                pnode = ppqueue.pop();
                if (q.val == pnode.val) {
                    found = q;
                    break;
                }
            } ;
        }
	 	if (found != null)
	 		return found;
        else
            return root;
    }
}