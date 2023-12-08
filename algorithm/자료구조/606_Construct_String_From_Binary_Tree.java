class Solution {
    public String tree2str(TreeNode t) {
        if(t==null) return "";

        String line = t.val+"";
        if(t.left!=null) {
            line += "(" + tree2str(t.left) + ")";
        }

        if(t.left==null && t.right!=null) {
            line +="()";
        }

        if(t.right!=null) {
            line += "(" + tree2str(t.right) + ")";
        }

        return line;
    }
}