import java.util.HashMap;

public class FindSingleNum {

    public static int singleNumber(int[] nums){ //time: O(n) - space: O(n)
        if(nums.length==1) return nums[0];

        int[] reference = new int[nums.length+1];
        
        for(int i = 0; i<nums.length; i++)
            reference[nums[i]]+=1;

        for(int i = 0; i<nums.length+1; i++)
            if(reference[i]==1)
                return i;
        return -1;
    }

    public static int singleNumber2(int[] nums){ //time: O(n) - space: O(n) -> same as previous approach in terms of logic
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int num:nums)
            if(map.containsKey(num))
                map.put(num, map.get(num)+1);
            else
                map.put(num, 1);
        
        for(HashMap.Entry<Integer, Integer> entry:map.entrySet())
            if(entry.getValue()==1) return entry.getKey();
        return -1;
    }

    public static int singleNumber3(int[] nums){ //time: O(n) - space: O(1) -> using bitwise
        if(nums.length==1) return nums[0];
        int returned = nums[0];
        for(int i = 1; i<nums.length; i++)
            returned^=nums[i];
        return returned;
    }

    public static void main(String[] args){
        int[] nums = {1,2,3,2,3,1,4};
        System.out.printf("singleNumber: %d\n", singleNumber(nums));   
        // System.out.printf("singleNumber: %d\n", singleNumber2(nums));      
        // System.out.printf("singleNumber: %d\n", singleNumber3(nums));        
    }
}
