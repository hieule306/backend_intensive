import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Admin {

    private static final Scanner sc = new Scanner(System.in);

    private List<String> firstNames;
    private List<String> lastNames;
    private Set<String> userNames;
    private List<Boolean> lockStatus;
    private List<String> lines;
    public boolean exitStatus = false;

    public Admin() {
        this.firstNames = new ArrayList<>();
        this.lastNames = new ArrayList<>();
        this.lockStatus = new ArrayList<>();
        this.userNames = new HashSet<>();
        this.lines = FileManager.readLines();

        int step = 0;
        int i = 0;
        if (this.lines == null) return;
        while (i < this.lines.size()) {
            String line = lines.get(i);
            String[] parts = line.split(":", 2);
            if (step == 0) this.firstNames.add(parts[1].trim());
            if (step == 1) this.lastNames.add(parts[1].trim());
            if (step == 3) this.userNames.add(parts[1].trim());
            if (step == 5) this.lockStatus.add(parts[1].trim().charAt(0) != '0');
            if (step == 6) step = -1;
            i++;
            step++;
        }
    }

    // --------------------- REGISTER --------------------
    public void register() {
        System.out.println("Enter First Name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter Last Name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter DOB: ");
        String dob = sc.nextLine();

        String userName;
        while (true) {
            System.out.println("Enter username: ");
            userName = sc.nextLine();
            if (userNames.contains(userName)) System.out.println("Username exists");
            else break;
        }

        System.out.println("Enter password: ");
        String password = sc.nextLine();

        String entry = "FirstName:" + firstName + "\nlastName:" + lastName
                + "\nDOB:" + dob + "\nusername:" + userName
                + "\npassword:" + password + "\nlocked:0\n------------\n";

        FileManager.appendLine(entry);
        firstNames.add(firstName);
        lastNames.add(lastName);
        userNames.add(userName);
        lockStatus.add(false);
    }

    // --------------------- VIEW --------------------
    public void view() {
        int decision;
        while (true) {
            System.out.println("1. Show all | 2. Find by name");
            decision = sc.nextInt();
            sc.nextLine();
            if (decision == 1 || decision == 2) break;
        }

        lines = FileManager.readLines();
        if (decision == 1) {
            for (String line : lines) System.out.println(line);
        } else {
            findByName();
        }
    }

    private void checkValid(String query, List<String> reference, List<Integer> indices) {
        for (int i = 0; i < reference.size(); i++) {
            if (reference.get(i).toLowerCase().contains(query.toLowerCase()))
                indices.add(i);
        }
    }

    private void findByName() {
        while (true) {
            System.out.println("Input query");
            String query = sc.nextLine();
            List<Integer> indicesFirst = new ArrayList<>();
            List<Integer> indicesLast = new ArrayList<>();

            checkValid(query, firstNames, indicesFirst);
            checkValid(query, lastNames, indicesLast);

            if (indicesFirst.isEmpty() && indicesLast.isEmpty()) continue;

            Deque<Integer> indices = new ArrayDeque<>();
            int tf = 0, tl = 0;
            while (tf < indicesFirst.size() && tl < indicesLast.size()) {
                int fi = indicesFirst.get(tf), li = indicesLast.get(tl);
                if (fi < li)       { indices.add(fi); tf++; }
                else if (fi > li)  { indices.add(li); tl++; }
                else               { indices.add(fi); tf++; tl++; }
            }
            while (tf < indicesFirst.size()) indices.add(indicesFirst.get(tf++));
            while (tl < indicesLast.size())  indices.add(indicesLast.get(tl++));

            int count = 0, step = 0, i = 0;
            StringBuilder result = new StringBuilder();
            while (i < lines.size() && !indices.isEmpty()) {
                result.append(lines.get(i)).append("\n");
                if (step == 6) {
                    step = -1;
                    if (count == indices.peekFirst()) {
                        indices.pollFirst();
                        System.out.print(result);
                    }
                    count++;
                    result = new StringBuilder();
                }
                step++;
                i++;
            }
            break;
        }
    }

    // --------------------- LOGIN --------------------
    public void login() {
        lines = FileManager.readLines();
        System.out.println("Enter Username: ");
        String userName = sc.nextLine();

        int i = 0, index = 0, step = 0;
        String password = "";

        while (i < lines.size()) {
            if (step == 3) {
                String[] parts = lines.get(i).split(":", 2);
                String refUserName = parts[1].trim();
                if (refUserName.equals(userName)) {
                    String[] passParts = lines.get(i + 1).split(":", 2);
                    password = passParts[1].trim();
                    break;
                }
            }
            if (step == 6) { step = -1; index++; }
            i++;
            step++;
        }

        if (password.isEmpty()) { System.out.println("Username not found!"); return; }
        if (lockStatus.get(index)) { System.out.println("Account is locked!"); return; }

        int attempts = 3;
        while (attempts > 0) {
            System.out.println("Enter Password: ");
            String entered = sc.nextLine();
            if (entered.equals(password)) {
                System.out.println("Login successful!");
                exitStatus = true;
                break;
            }
            if (attempts == 1) {
                System.out.println("Login failed!");
                lockStatus.set(index, true);
                lines.set(5 + index * 7, "locked:1");
                FileManager.writeLines(lines);
            } else {
                System.out.println("Wrong Password! Enter again");
            }
            attempts--;
        }
    }
}