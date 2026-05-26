import java.util.Arrays;

public class triangularTriplet {
    public static int solution(int[] A){
        Arrays.sort(A);
        for(int left = 0; left<A.length; left++){
            int right = A.length-1;
            int mid = left+1;
            while(mid<right){
                if((A[left] + A[right]>A[mid]) && (A[left] + A[mid]>A[right]) && (A[mid] + A[right]>A[left])){
                    return 1;
                }

                if(A[left]+A[mid]<A[right]){
                    boolean loop = false;
                    while(mid<right && A[right]==A[right-1]){
                        loop = true;
                        right--;
                    }
                    if(!loop) right--;
                }
                else{
                    boolean loop = false;
                    while(mid<right && A[mid]==A[mid+1]){
                        mid++;
                        loop = true;
                    }
                    if(!loop) mid++;
                }
            }
        }
        return 0;
    }

    public static int solution_2(int[] A){
        if(A.length<3) return 0;
        Arrays.sort(A);
        for(int i = 0; i<A.length-2; i++)
            if(A[i] + A[i+1] > A[i+2])
                return 1;
        return 0;
    }


    public static void main (String[] args){
        System.out.println(solution(new int[] {10, 2, 5, 1, 8, 20}));
        System.out.println(solution_2(new int[] {10, 2, 5, 1, 8, 20}));
        System.err.println("-------");
        System.out.println(solution(new int[] {10, 50, 5, 1}));
        System.out.println(solution_2(new int[] {10, 50, 5, 1}));
        System.err.println("-------");
        System.out.println(solution(new int[] {-5, -4, -3, 1, 2, 100}));
        System.out.println(solution_2(new int[] {-5, -4, -3, 1, 2, 100}));

    }
}
