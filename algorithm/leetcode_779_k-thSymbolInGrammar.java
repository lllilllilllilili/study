class Solution {
    public int kthGrammar(int n, int k) {
        if (n == 1) {
            return 0;
        }

        // 절반 지점을 계산
        int mid = (int) Math.pow(2, n - 2);

        if (k <= mid) {
            return kthGrammar(n - 1, k);
        } else {
            return 1 - kthGrammar(n - 1, k - mid);
        }
    }
}