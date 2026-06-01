import java.util.ArrayList;
import java.util.List;

public class removeDuplicates {
    public static void printArr(int [] arr){
        for(int num: arr)
            System.out.printf("%d ", num);
        System.out.println("");
    }
    // ------------------------------
    public static int[] solution(int[] arr){
        List<Integer> track = new ArrayList<>();
        int i = 0;
        track.add(arr[i]);
        i++;
        while(i<arr.length){
            if(arr[i]!=arr[i-1])
                track.add(arr[i]);
            i++;
        }
        int[] ans = new int[track.size()];
        int j = 0;
        for(int num: track){
            ans[j++] = num;
        }
        return ans;
    }


    // ------------------------------
    public static void main(String[] args){
        int[] arr = {1, 1, 2};
        printArr(solution(arr));

        arr = new int[] {1};
        printArr(solution(arr));
    }
}
