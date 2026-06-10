package app;

import java.util.List;
import java.util.Scanner;
import model.Finance;
import model.User;
import service.UserService;
import service.UserService.AuthResult;

public class AdminConsoleApp {

    private final Scanner scanner;
    private final UserService userService;

    public AdminConsoleApp(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void run() {
        handleInitialDeletePrompt();

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("1. Register | 2. View Users | 3. Login");
            int decision = readMenuChoice(1, 3);

            if (decision == 1) {
                registerFlow();
            } else if (decision == 2) {
                viewFlow();
            } else {
                loggedIn = loginFlow();
            }
        }
    }

    private void handleInitialDeletePrompt() {
        System.out.println("Delete current database: 1 - Yes | 2 - No");
        int delete = readMenuChoice(1, 2);
        if (delete == 1) {
            userService.resetDatabase();
        }
    }

    private void registerFlow() {
        try {
            System.out.println("Enter First Name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter Last Name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter DOB:");
            String dob = scanner.nextLine();

            System.out.println("Enter username:");
            String username = scanner.nextLine();

            System.out.println("Enter password:");
            String password = scanner.nextLine();

            userService.register(firstName, lastName, dob, username, password);
            System.out.println("Registration successful");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewFlow() {
        System.out.println("1. Show all | 2. Find by name");
        int choice = readMenuChoice(1, 2);

        if (choice == 1) {
            printUsers(userService.getAllUsers());
            return;
        }

        System.out.println("Input query");
        String query = scanner.nextLine();
        printUsers(userService.findByName(query));
    }

    private boolean loginFlow() {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();

        int attempts = 3;
        while (attempts > 0) {
            System.out.println("Enter Password:");
            String password = scanner.nextLine();

            AuthResult result = userService.login(username, password, attempts);
            if (result.getStatus() == AuthResult.Status.SUCCESS) {
                System.out.println("Login successful!");
                loginOptions(username);
                return true;
            }

            if (result.getStatus() == AuthResult.Status.USER_NOT_FOUND) {
                System.out.println("Username not found!");
                return false;
            }

            if (result.getStatus() == AuthResult.Status.LOCKED) {
                System.out.println("Account is locked!");
                return false;
            }

            if (result.getStatus() == AuthResult.Status.LOCKED_NOW) {
                System.out.println("Login failed! Account is now locked.");
                return false;
            }

            attempts--;
            if (attempts > 0) {
                System.out.println("Wrong Password! Attempts left: " + attempts);
            }
        }

        return false;
    }

    private void loginOptions(String username){
        Finance focusFinance = userService.getFinance(username);
        boolean loggedOut = false;
        while(!loggedOut) { 
            System.out.println("1. Register New Card | 2. Deposit | 3. Withdraw | 4. View Balance | 5. View Transaction History | 6. Logout");
            int decision = readMenuChoice(1, 6);
            if(decision==1){
                focusFinance.RegisterCard();
            }
            else if(decision==2){
                focusFinance.Deposit();
            }
            else if(decision==3){
                focusFinance.Withdraw();
            }
            else if(decision==4){
                focusFinance.viewBalance();
            }
            else if(decision==5){
                focusFinance.viewTransactionHistory();
            }
            else loggedOut = true;
        }
    }


    private void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found");
            return;
        }

        for (User user : users) {
            System.out.println("FirstName:" + user.firstName);
            System.out.println("lastName:" + user.lastName);
            System.out.println("DOB:" + user.dob);
            System.out.println("username:" + user.username);
            System.out.println("password:" + user.password);
            System.out.println("locked:" + (user.locked ? "1" : "0"));
            System.out.println("------------");
        }
    }

    private int readMenuChoice(int min, int max) {
        while (true) {
            String raw = scanner.nextLine();
            try {
                int parsed = Integer.parseInt(raw.trim());
                if (parsed >= min && parsed <= max) {
                    return parsed;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Enter again.");
        }
    }
}
