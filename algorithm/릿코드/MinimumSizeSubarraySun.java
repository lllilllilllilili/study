class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int min = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;
        for(int right = 0;
            right<nums.length; right++
        ) { //nums 를 돌면서
            sum+=nums[right]; //sum에 누적값을 계속 더해,
            while(sum>=target) {
                //예를 들어서
                //하나씩 늘려가면서
                min = Math.min(min, right-left+1);
                sum-=nums[left++]; //위에서 더한거 고스란히 반납하네
                /*
                결국 sum 이하 까지 내려가야 탈출하는거니
                정확히 sum이 target값과 일치할 때 나올거고
                지금 값도 min값을 구하는것이니 방향성이 맞음
                 */
            }
        }
        return min == Integer.MAX_VALUE?0:min;
    }
}