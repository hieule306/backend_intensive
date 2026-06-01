public class TheFeast {
    public static int theFeast(int n, int c, int m){
        int total = n/c;
        int wrappers = total;
        while(wrappers>=m){
            int newBars = wrappers/m;
            total+=newBars;
            wrappers%=m;
            wrappers+=newBars;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(theFeast(15, 3, 2));
        System.out.println(theFeast(10, 2, 5));
        System.out.println(theFeast(12, 4, 4));
        System.out.println(theFeast(6, 2, 2));
    }
}
