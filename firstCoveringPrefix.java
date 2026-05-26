import java.util.HashSet;
import java.util.Set;

public class firstCoveringPrefix {

    public static int solution(int[] A){
        Set<Integer> uniques = new HashSet<>();
        for(int i = 0; i<A.length; i++){
            uniques.add(A[i]);
        }

        for(int i = 0; i<A.length; i++){
            if(uniques.contains(A[i]))
                uniques.remove(A[i]);
            if(uniques.isEmpty())
                return i;
        }
        return A.length-1;
    }
    public static void main(String[] args){
        System.out.println(solution(new int[] {2, 2, 1, 0, 1}));
        System.out.println(solution(new int[] {1, 1, 1, 1, 1}));
        System.out.println(solution(new int[] {1, 2, 3, 2, 1}));
        System.out.println(solution(new int[] {3, 3, 2, 1}));
        System.out.println(solution(new int[] {0, 1, 0, 0}));
    }
}
