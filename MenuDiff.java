public class MenuDiff{
	    /*
    At DoorDash, menus are updated daily even hourly to keep them up-to-date.
    Each menu can be regarded as a tree. When the merchant sends us the latest menu,
    can we calculate how many nodes have changed/added/deleted?
    class Node {
        String key;
        int value;
        List<Node> children;
    }
    Assume there are no duplicate nodes with the same key.
    Output: Return the number of changed nodes in the tree.
    */
    static class TreeNode{
        int key;
        int value;
        List<TreeNode> children;
    }
    public int findDiffNodes(TreeNode root1, TreeNode root2) {
         if (root1 == null && root2 == null) return 0;
         if ((root1 != null && root2 == null) || (root1 == null && root2 != null) || (root1.key != root2.key)) {
             return countNodes(root1) + countNodes(root2);
         }
         int diffCount = 0;
         if (root1.value != root2.value) diffCount += 2;
         List<TreeNode> children1 = root1.children;
         List<TreeNode> children2 = root2.children;
         Map<Integer, TreeNode> childrenMap1 = new HashMap<>();
         for (TreeNode child1 : children1) {
             childrenMap1.put(child1.key, child1);
         }
         Map<Integer, TreeNode> childrenMap2 = new HashMap<>();
         for (TreeNode child2 : children2) {
             childrenMap2.put(child2.key, child2);
         }

         for (int key1 : childrenMap1.keySet()) {
             TreeNode child1 = childrenMap1.get(key1);
             if (!childrenMap2.containsKey(key1)) {
                 diffCount += countNodes(child1);
             } else {
                 TreeNode child2 = childrenMap2.get(key1);
                 diffCount += findDiffNodes(child1, child2);
                 childrenMap2.remove(key1);
             }
         }
         for (TreeNode child2 : childrenMap2.values()) {
             diffCount += countNodes(child2);
         }
         return diffCount;
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        int count = 1;
        for (TreeNode child : node.children) {
             count += countNodes(child);
        }
        return count;
    }
}