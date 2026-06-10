package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import model.Finance;
import model.User;

public class FileUserRepository implements UserRepository {

    private final Path storagePath;
    private final Path financePath;

    public FileUserRepository() {
        this.storagePath = resolvePath(true);
        this.financePath = resolvePath(false);
    }

    public FileUserRepository(Path storagePath, Path financePath) {
        this.storagePath = storagePath;
        this.financePath = financePath;
    }

    @Override
    public List<User> findAll() {
        List<String> lines = readLines(true);
        List<User> users = new ArrayList<>();
        
        int i = 0;
        while(i+5<lines.size()){
            if ("------------".equals(lines.get(i).trim())) {
                i++;
                continue;
            }

            String firstName = parseField(lines, i, "FirstName");
            String lastName = parseField(lines, i + 1, "lastName");
            String dob = parseField(lines, i + 2, "DOB");
            String username = parseField(lines, i + 3, "username");
            String password = parseField(lines, i + 4, "password");
            String lockedRaw = parseField(lines, i + 5, "locked");
            boolean locked = "1".equals(lockedRaw);

            users.add(new User(firstName, lastName, dob, username, password, locked));

            i += 6;
            if (i<lines.size() && "------------".equals(lines.get(i).trim())) i++;
        }

        return users;
    }

    @Override
    public void saveAll(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.addAll(user.toStorageBlock());
        }

        try {
            Files.write(storagePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save users", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.deleteIfExists(storagePath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to delete users file", e);
        }
    }

    private List<String> readLines(boolean isUsers) {
        if(isUsers){
            try {
                if (!Files.exists(storagePath)) {
                    return new ArrayList<>();
                }
                return Files.readAllLines(storagePath);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read users file", e);
            }
        }
        else{
            try {
                if (!Files.exists(financePath)) {
                    return new ArrayList<>();
                }
                return Files.readAllLines(financePath);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read finance file", e);
            }
        }
    }

    private static String parseField(List<String> lines, int index, String expectedField) {
        if (index >= lines.size()) {
            throw new IllegalStateException("Malformed user record: missing field " + expectedField);
        }

        String line = lines.get(index);
        String[] parts = line.split(":", 2);
        if (parts.length != 2 || !expectedField.equals(parts[0].trim())) {
            throw new IllegalStateException("Malformed user record: expected " + expectedField + " at line " + (index + 1));
        }
        return parts[1].trim();
    }

    private static Path resolvePath(boolean isUsers) {
        Path nested = isUsers?Paths.get("users.txt"):Paths.get("finances.txt");
        if (Files.exists(nested)) {
            return nested;
        }
        return isUsers?Paths.get("users.txt"):Paths.get("finances.txt");
    }


    // -------------------- Find card info
    @Override
    public List<Finance> findFinances(){
        List<String> lines = readLines(false);
        List<Finance> finances = new ArrayList<>();
        
        int i = 0;
        while(i+3<lines.size()){
            if ("------------".equals(lines.get(i).trim())) {
                i++;
                continue;
            }

            String username = parseField(lines, i, "username");
            String cardNum = parseField(lines, i + 1, "cardNum");
            String bank = parseField(lines, i + 2, "bank");
            String balance = parseField(lines, i + 3, "balance");

            finances.add(new Finance(username, cardNum, bank, balance));

            i+=4;
            if (i<lines.size() && "------------".equals(lines.get(i).trim())) i++;
        }

        return finances;
    }
}
