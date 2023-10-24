class Solution {
    public boolean isPowerOfFour(int n) {
        if (n == 0) {
            return false;
        }
        int logValue = (int) (Math.log(n) / Math.log(4));
        return Math.pow(4, logValue) == n;
    }
}