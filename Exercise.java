public class Exercise {
    public static void main(String[] args) {
        int[] nums = {3, 4, 5};

        if(nums.length==0) return;
        int max_val = nums[0], min_val = nums[0], total = 0;
        for(int num: nums){
            if(num>max_val) max_val = num;
            if(num<min_val) min_val = num;
            total+=num; 
        }
        double avg = (double) total/nums.length;
        System.out.println("Max: " + max_val);
        System.out.println("Min: " + min_val);
        System.out.printf("Average: %.2f\n", avg);
    }
}
