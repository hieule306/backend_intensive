public class BinaryGap {
    public static String convertBinary(int N){
        if(N==0) return "0";
        String binary = "";
        while(N>=1){
            binary = (char)(N%2 + '0') + binary;
            N/=2;
        }
        
        return binary;
    }

    public static int solution(int N){
        String binary = convertBinary(N);
        int left = -1, max_length = 0;
        System.out.println(binary);
        for(int right = 0; right<binary.length(); right++){
            if(binary.charAt(right)=='1'){
                // while(binary.charAt(left)!='1' && left<=right){
                //     left++;
                // }
                // System.out.println("Left: " + left + " Right: " + right);
                if(left!=-1) max_length = Math.max(max_length, right-left-1);
                left = right;
            }
        }
        return max_length;
    }
    public static void main(String[] args) {
        // System.out.println(convertBinary(529));
        System.out.println(solution(32));
    }
}
