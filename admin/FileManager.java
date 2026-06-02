import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    static final Path PATH = Paths.get("users.txt");

    public static List<String> readLines() {
        try {
            return Files.readAllLines(PATH);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void appendLine(String content) {
        try {
            Files.writeString(PATH, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {}
    }

    public static void writeLines(List<String> lines) {
        try {
            Files.write(PATH, lines);
        } catch (IOException e) {}
    }

    public static void delete() {
        try {
            Files.delete(PATH);
        } catch (IOException e) {}
    }
}