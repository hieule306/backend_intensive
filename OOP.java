import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class OOP {
    static Path path = Paths.get("users.txt");
    static Scanner sc = new Scanner(System.in);

    public static class Admin{
        List<String> firstNames;
        List<String> lastNames;
        Set<String> userNames;
        List<Boolean> lockStatus;
        List<String> lines;
        boolean exitStatus = false;

        Admin(){
            this.firstNames = new ArrayList<>();
            this.lastNames = new ArrayList<>();
            this.lockStatus = new ArrayList<>();
            this.userNames = new HashSet<>();
            try{
                this.lines = Files.readAllLines(path);
            }catch(IOException e){}
            
            int step = 0;
            int i = 0;
            if(this.lines==null) return;
            while(i<this.lines.size()){
                String line = lines.get(i);
                String[] parts = line.split(":", 2);
                if(step==0){
                    this.firstNames.add(parts[1].trim());
                }
                if(step==1){
                    this.lastNames.add(parts[1].trim());
                }
                if(step==3){
                    this.userNames.add(parts[1].trim());
                }
                if(step==5){
                    boolean check = parts[1].trim().charAt(0)=='0'?false:true;
                    this.lockStatus.add(check);
                }
                if(step==6){
                    step = -1;
                }
                i++;
                step++;
            }
        }

        // --------------------- LOGIC FOR REGISTER --------------------
        public void register(){
            System.out.println("Enter First Name: ");
            String firstName = sc.nextLine();
            System.out.println("Enter Last Name: ");
            String lastName = sc.nextLine();
            System.out.println("Enter DOB: ");
            String DOB = sc.nextLine();
            String userName;
            while (true) { 
                System.out.println("Enter username: ");
                userName = sc.nextLine();
                if(userNames.contains(userName)){
                    System.out.println("Username exists");
                }
                else break;
            }

            System.out.println("Enter password: ");
            String password = sc.nextLine();

            String line = "FirstName:" + firstName + "\nlastName:" + lastName + "\nDOB:" + DOB + "\nusername:" + userName + "\npassword:" + password + "\nlocked:0" + "\n------------\n";
            
            try {
                Files.writeString(path, line, StandardOpenOption.CREATE,  StandardOpenOption.APPEND);
            } catch (IOException e) {
            }

            firstNames.add(firstName);
            lastNames.add(lastName);
            userNames.add(userName);
            lockStatus.add(false);
        }

        // --------------------- LOGIC FOR VIEW --------------------
        public void view(){
            int decision;
            while(true){
                System.out.println("1. Show all | 2. Find by name");
                decision = sc.nextInt();
                sc.nextLine();
                if(decision==1 || decision==2) break;
            }

            try{
                lines = Files.readAllLines(path);
                if(decision==1){
                    for(String line: lines)
                        System.out.println(line);
                }
                else find_by_name();
            }catch(IOException e){}

        }

        public void check_valid(String query, List<String> reference, List<Integer> indices){
            for(int i = 0; i<reference.size(); i++){
                if(reference.get(i).toLowerCase().contains(query.toLowerCase()))
                    indices.add(i);
            }
        }

        public void find_by_name(){            
            while(true){
                System.out.println("Input query");
                String query = sc.nextLine();
                List<Integer> indices_first = new ArrayList<>();
                List<Integer> indices_last = new ArrayList<>();
                
                check_valid(query, firstNames, indices_first);
                check_valid(query, lastNames, indices_last);

                if(indices_first.size()==0 && indices_last.size()==0) continue;

                Deque<Integer> indices = new ArrayDeque<>();
                int track_first = 0, track_last = 0;

                while(track_first<indices_first.size() && track_last<indices_last.size()){
                    int first_index = indices_first.get(track_first);
                    int last_index = indices_last.get(track_last);
                    if(first_index<last_index){
                        indices.add(first_index);
                        track_first++;
                    }
                    else if(first_index>last_index){
                        indices.add(last_index);
                        track_last++;
                    }
                    else{
                        indices.add(first_index);
                        track_last++;
                        track_first++;
                    }
                }

                while(track_first<indices_first.size()){
                    indices.add(indices_first.get(track_first++));
                }
                while(track_last<indices_last.size()){
                    indices.add(indices_last.get(track_last++));
                }

                int count = 0;
                int step = 0;
                int i = 0;
                String result = "";
                while(i<lines.size() && !indices.isEmpty()){
                    result+=lines.get(i) + "\n";
                    if(step==6){
                        step = -1;
                        if(count==indices.peekFirst()){
                            indices.pollFirst();
                            System.out.printf(result);
                        }
                        count++;
                        result = "";
                    }
                    step++;
                    i++;
                }
                break;
            }
        }
        // -----------------------------------------

        // --------------------- LOGIC FOR LOGIN --------------------
        public void login(){
            try{
                lines = Files.readAllLines(path);
            }catch(IOException e){}
            
            System.out.println("Enter Username: ");
            String userName = sc.nextLine(); 
            int i = 0;
            int index = 0;
            int step = 0;
            String password = "";
            while(i<lines.size()){
                if(step==3){
                    String getUserName = lines.get(i);
                    String[] parts = getUserName.split(":", 2);
                    String ref_userName = parts[1].trim();
                    if(ref_userName.length()==userName.length()){
                        int track_ref = 0, track_user = 0;
                        while(track_ref<ref_userName.length()){
                            if(ref_userName.charAt(track_ref++)!=userName.charAt(track_user++))
                                break;
                        }
                        if(track_ref==ref_userName.length()){
                            String getPassword = lines.get(i+1);
                            parts = getPassword.split(":", 2);
                            password = parts[1].trim();
                            break;
                        }
                    }
                }
                if(step==6){
                    step = -1;
                    index++;
                }
                i++;
                step++;
            }
            if(password.isEmpty()){
                System.out.println("Username not found!");
                return;
            }
            
            if(lockStatus.get(index)){
                System.out.println("Account is locked!");
                return;
            }

            int attempts = 3;
            while(attempts>0){
                System.out.println("Enter Password: ");
                String enteredPassword = sc.nextLine();
                if(enteredPassword.length()==password.length()){
                    int index_password = 0;
                    while(index_password<password.length()){
                        if(enteredPassword.charAt(index_password)!=password.charAt(index_password))
                            break;
                        index_password++;
                    }
                    if(index_password==password.length()){
                        System.out.println("Login successful!");
                        exitStatus = true;
                        break;
                    }
                }
                if(attempts==1){
                    System.out.println("Login failed!");
                    lockStatus.set(index, true);
                    lines.set(5+index*7, "locked:1");
                    try {
                        Files.write(path, lines);
                    } catch (IOException e) {
                    }
                }
                else
                    System.out.println("Wrong Password! Enter again");
                attempts--;
            }

        }

    }

    public static void main(String[] args){
        int delete;
        while(true){
            System.out.println("Delete curr database: 1 - true | 2 - false");
            delete = sc.nextInt();
            sc.nextLine();
            if(delete==1 || delete==2) break;
        }

        if(delete==1){
            try{
                Files.delete(path);
            }
            catch(IOException e){}
        }

        Admin admin = new Admin();

        while(!admin.exitStatus){
            System.out.println("1. Register | 2. View Registered Users | 3. Login");
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
