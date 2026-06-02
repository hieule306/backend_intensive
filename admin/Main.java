import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int delete;
        while (true) {
            System.out.println("Delete current database: 1 - Yes | 2 - No");
            delete = sc.nextInt();
            sc.nextLine();
            if (delete == 1 || delete == 2) break;
        }

        if (delete == 1) FileManager.delete();

        Admin admin = new Admin();

        while (!admin.exitStatus) {
            System.out.println("1. Register | 2. View Users | 3. Login");
            int decision = sc.nextInt();
            sc.nextLine();
            
            if(decision!=1 && decision!=2 && decision!=3){
                System.out.println("Enter again.");
                continue;
            }

            if(decision==1)
                admin.register();
            else if(decision==2)
                admin.view();
            else
                admin.login();
        
        }
    }
}