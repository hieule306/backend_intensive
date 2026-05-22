public class sumEven_with_condition {
    public static int recursive(int[] A, int pos, int i){ // recursive -> can take 2 recursive all each turn -> at worst case scenario -> 2^n
        if(i==A.length) return 0;
        int skip = recursive(A, pos, i+1);
        int addition = 0;
        if(pos%2==0){
            addition = A[i];
        } 
        else addition = -A[i];
        return Math.max(skip, addition + recursive(A, pos+1, i+1));
    }

    public static int solution(int[] A){ //recursive
        return recursive(A, 0, 0);
    }

    public static int solution_2(int[] A){ // state tracking
        int odd = 0, even = 0;

        for(int i = 0; i<A.length; i++){
            int newOdd = Math.max(odd, even-A[i]);
            int newEven = Math.max(even, odd+A[i]);
            odd = newOdd;
            even = newEven;
        }

        return even%1000000000;
    }
    public static void main(String[] args){
        System.out.println(solution(new int[]{4, 1, 2, 3}));
        System.out.println(solution_2(new int[]{4, 1, 2, 3}));
        System.out.println(solution(new int[]{1, 2, 3, 3, 2, 1, 5}));
        System.out.println(solution_2(new int[]{1, 2, 3, 3, 2, 1, 5}));
    }
}
