class Solution {
    static final int MOD = 1_000_000_007;

    public int numFactoredBinaryTrees(int[] arr) {
        int n = arr.length;
        long[] dp = new long[n];
        Arrays.fill(dp, 1);

        Arrays.sort(arr);

        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < n; i++) {
            index.put(arr[i], i);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] % arr[j] == 0) { // arr[j] is a potential left child
                    int rightChild = arr[i] / arr[j];
                    if (index.containsKey(rightChild)) {
                        dp[i] = (dp[i] + dp[j] * dp[index.get(rightChild)]) % MOD;
                    }
                }
            }
        }

        long result = 0;
        for (long x : dp) {
            result += x;
        }
        return (int) (result % MOD);
    }
}