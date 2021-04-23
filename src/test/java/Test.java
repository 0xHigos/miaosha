public class Test {

    public static void main( String[] args ) {
        Solution s = new Solution();
        s.removeDuplicates(new int[]{1,1,2});
    }
}
class Solution {
    public int removeDuplicates(int[] nums) {
        int i = 0,j = 1;
        int n = nums.length;
        if(n == 0){
            return 1;
        }
        while(j < n){
            while(j < n && nums[j] == nums[i]){
                j++;
            }
            nums[++i] = nums[j];
        }
        return i;
    }
}
