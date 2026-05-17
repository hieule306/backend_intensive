import java.util.Stack;

public class Palindrome {
    public static boolean isPalindrome(int[] nums){
        int left = 0, right = nums.length-1;
        while(left<right){
            boolean cond = nums[left++]==nums[right--];
            if(!cond) return false;
        }
        return true;
    }

    // check if array contains all sequences of palindromes
    public static boolean isPalindrome_all(int[] nums){
        Stack<Integer> stack = new Stack<>();
        
        for(int num : nums){
            if(!stack.isEmpty() && stack.peek()==num){
                stack.pop();  
            } 
            else if(stack.size() >= 2){ // odd case
                int top = stack.pop();

                if(stack.peek()==num){
                    stack.pop();
                } 
                else{
                    stack.push(top); 
                    stack.push(num);
                }
            }
            else
                stack.push(num);
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, 1, 0, 1};
        System.out.printf("isPalindrome: %b\n", isPalindrome(nums));

        // nums = new int[] {1, 0, 1, 1, 0, 1, 2, 3, 3, 2};
        // nums = new int[] {1, 2, 1, 3, 3};
        // System.out.printf("isPalindrome_all: %b\n", isPalindrome_all(nums));
    }
}
