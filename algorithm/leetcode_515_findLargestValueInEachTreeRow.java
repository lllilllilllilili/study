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
            int max = Integer.MIN_VALUE; // 각 레벨의 초기 최대값을 설정합니다.
            for (int i = 0; i < size; i++) { // 현재 레벨의 모든 노드를 순회합니다.
                TreeNode current = queue.poll();
                if (current.val > max) { // 현재 노드의 값이 현재 최대값보다 크면 업데이트합니다.
                    max = current.val;
                }

                if (current.left != null) { // 왼쪽 자식 노드가 있으면 큐에 추가합니다.
                    queue.add(current.left);
                }

                if (current.right != null) { // 오른쪽 자식 노드가 있으면 큐에 추가합니다.
                    queue.add(current.right);
                }
            }
            result.add(max); // 현재 레벨의 최대값을 결과 목록에 추가합니다.
        }
        return result;
    }
}