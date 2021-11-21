public class MaxPathSum{
	// Binary Tree ->
    // Find Max Path Sum from Leaf to Leaf ->
    // Some Leaves Have Tag, Find Max Path Sum from Tagged Leaves ->
    // Tagged Node May Not Be Leaf, Find Max Path Sum from Tagged Leaves
    static class Tree{
        int val;
        Tree left;
        Tree right;
        boolean tagged;
        public Tree(int val, Tree left, Tree right, boolean tagged){
            this.val = val;
            this.left = left;
            this.right = right;
            this.tagged = tagged;
        }
        public Tree(){}
    }
    static int maxSubSum = Integer.MIN_VALUE;
    public static int pathSumLtoL(Tree root){
        dfs(root);
        return maxSubSum;
    }
    private static int dfs(Tree root){
        if (root == null) return 0;
        int l = dfs(root.left), r = dfs(root.right);
        maxSubSum = Math.max(maxSubSum, l+r+root.val);
        return Math.max(l,r)+root.val;
    }
    private static int[] dfsTagged(Tree root){
        if (root == null) return new int[]{0,-1};
        int[] l = dfsTagged(root.left), r = dfsTagged(root.right);
        int ret = 0, flag = (l[1] == 1 || r[1] == 1)? 1 : -1;
        if (root.tagged){
            ret += root.val;
            flag = 1;
        }
        if (l[1] == 1 && r[1] == 1) {
            maxSubSum = Math.max(maxSubSum, l[0]+r[0]+root.val);
            ret += Math.max(l[0],r[0]) + root.val;
        }else if (l[1] == 1){
            maxSubSum = Math.max(maxSubSum, l[0]+root.val);
            ret += l[0]+root.val;
        }else if (r[1] == 1){
            maxSubSum = Math.max(maxSubSum, r[0]+root.val);
            ret += r[0]+root.val;
        }
        return new int[]{ret, flag};
    }
}