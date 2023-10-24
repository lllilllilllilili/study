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
import java.util.*;

public class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            int max = Integer.MIN_VALUE; // �� ������ �ʱ� �ִ밪�� �����մϴ�.
            for (int i = 0; i < size; i++) { // ���� ������ ��� ��带 ��ȸ�մϴ�.
                TreeNode current = queue.poll();
                if (current.val > max) { // ���� ����� ���� ���� �ִ밪���� ũ�� ������Ʈ�մϴ�.
                    max = current.val;
                }

                if (current.left != null) { // ���� �ڽ� ��尡 ������ ť�� �߰��մϴ�.
                    queue.add(current.left);
                }

                if (current.right != null) { // ������ �ڽ� ��尡 ������ ť�� �߰��մϴ�.
                    queue.add(current.right);
                }
            }
            result.add(max); // ���� ������ �ִ밪�� ��� ��Ͽ� �߰��մϴ�.
        }
        return result;
    }
}