import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileInput {

    /**
     * Reads the content of a file located at the given path and processes it based on the specified flags.
     * 
     * @param path               the path to the file
     * @param discardEmptyLines  if true, empty lines are discarded
     * @param trim               if true, leading and trailing whitespace is trimmed from each line
     * @return                   an array of strings containing the lines of the file
     */
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            if (discardEmptyLines) {
                lines.removeIf(line -> line.trim().isEmpty());
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

    /**
     * Parses the input data from the given array of strings and creates corresponding objects.
     * 
     * @param fileInput  an array of strings representing the lines of the file
     */
    public static void parseInput(String[] fileInput) {
        for (String line : fileInput) {
            String[] parts = line.split("\t");

            // Handle the first line separately to set start and end point names
            if (parts.length < 4) {
                FastestRoute.startPointName = parts[0];
                FastestRoute.endPointName = parts[1];
                continue;
            }

            String pointA = parts[0];
            String pointB = parts[1];
            int length = Integer.parseInt(parts[2]);
            int id = Integer.parseInt(parts[3]);
            new Road(pointA, pointB, length, id);
        }
    }
}
