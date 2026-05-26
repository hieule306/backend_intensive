import java.util.ArrayList;
import java.util.List;

public class numPairs {
    public static int solution(int[] A){
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();
        for(int num:A){
            if(num%2==0)
                evens.add(num);
            else odds.add(num);
        }
        long countEvens = 0;
        long countOdds = 0;
        int incrementOdds = odds.size()-1;
        int incrementEvens = evens.size()-1;
        
        for(int i = 0; i<evens.size()-1; i++){
            countEvens+=incrementEvens;
            incrementEvens--;
        }

        for(int i = 0; i<odds.size()-1; i++){
            countOdds+=incrementOdds;
            incrementOdds--;
        }

        return countEvens+countOdds>1000000000?-1: (int)(countEvens+countOdds);
    }
    public static void main(String[] args){
        System.out.println(solution(new int[] {2, 1, 5, -6, 9}));
    }
}
