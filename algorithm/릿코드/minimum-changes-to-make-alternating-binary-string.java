class Solution {
    public int minOperations(String s) {
        int cnt = 0;
        /*
        01 -> one 2
        10 -> zero 2
        0100
        one 3
        zero 1
        */
        int a = 0;
        int b = 0;
        for (int i=0; i<s.length(); i++) {
            if(i%2 == 0) {
                if(s.charAt(i) - '0' == 0){
                    a++;
                }else {
                    b++;
                }
            } else {
                if(s.charAt(i) - '0' == 1){
                    a++;
                }else {
                    b++;
                }
            }
        }
        return Math.min(a,b);
    }
}
