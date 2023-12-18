class Solution {
    public boolean isPalindrome(String s) {
        String s1 = s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");

        for(int i=0; i<s1.length()/2; i++) {
            if(s1.charAt(i) != s1.charAt(s1.length()-i-1)) {
                return false;
            }
        }
        return true;
    }
}

/**
 * 정규식 표현에 대한것 알기 -> 문자열만 뽑아서
 *
 */