import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInput {
    // 
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            if (discardEmptyLines) {
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) {
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Aim of this method parsing bnf key value pairs and store them in a collection.
    public static Map<String, String> parseInput(String[] arr) {
        // Map used as a collection.
        Map<String, String> map = new HashMap<>();
        for (String line : arr) {
            String[] newLine = line.split("->");// Parse the arr(input) to pairs.
            map.put(newLine[0], newLine[1]);// Add them into map.
        }
        return map;
    }
}
