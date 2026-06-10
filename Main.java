import app.AdminConsoleApp;
import java.util.Scanner;
import repository.FileUserRepository;
import service.UserService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService(new FileUserRepository());
        AdminConsoleApp app = new AdminConsoleApp(scanner, userService);
        app.run();
    }
}