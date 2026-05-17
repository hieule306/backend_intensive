public class checkPrimeNumber {
    public static boolean isPrimeNumber(int num){
        if(num<2) return false;
        if(num==2) return true;

        int sqrt = (int) Math.sqrt(num);
        for(int i = 2; i<=sqrt; i++)
            if(num%i==0)
                return false;
        return true;
    }
    public static void main(String[] args){
        System.out.println(isPrimeNumber(1));
        System.out.println(isPrimeNumber(2));
        System.out.println(isPrimeNumber(3));
        System.out.println(isPrimeNumber(71));
    }
}
