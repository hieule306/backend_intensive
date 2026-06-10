package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String firstName;
    public String lastName;
    public String dob;
    public String username;
    public String password;
    public boolean locked;

    public User(){
    }

    public User(String firstName, String lastName, String dob, String username, String password, boolean locked) {
        this.firstName = requireNonBlank(firstName, "firstName");
        this.lastName = requireNonBlank(lastName, "lastName");
        this.dob = requireNonBlank(dob, "dob");
        this.username = requireNonBlank(username, "username");
        this.password = requireNonBlank(password, "password");
        this.locked = locked;
    }

    public List<String> toStorageBlock() {
        List<String> block = new ArrayList<>();
        block.add("FirstName:" + firstName);
        block.add("lastName:" + lastName);
        block.add("DOB:" + dob);
        block.add("username:" + username);
        block.add("password:" + password);
        block.add("locked:" + (locked ? "1" : "0"));
        block.add("------------");
        return block;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value.trim();
    }
}
