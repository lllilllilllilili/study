import java.util.*;
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        List<Integer> answer = new ArrayList<>();
        int k = 0;
        for(int a : nums1) {
            if (k++>=m) break;
            answer.add(a);
        }
        k = 0;
        for(int b : nums2) {
            if (k++>=n) break;
            answer.add(b);
        }
        Collections.sort(answer);
        int idx = 0;
        for(int c : answer) {
            nums1[idx++] = c;
        }
    }
}
/**
 * 너무 오랜만에 풀어서 List 활용법 긴가민가..
 * 정렬 기능은 -> Collections.sort
 * 문제는 간단하다. 두개 배열을 merge 하는것이다.
 * 다만 0부터 offset 까지 (여기서는 각각 n, m 이 된다.)
 */