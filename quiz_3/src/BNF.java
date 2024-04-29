import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class BNF {
    public static void main(String[] args) throws Exception {
        String INPUT_PATH = args[0]; 
        String OUTPUT_PATH = args[1];  

        // Redirecting system output stream to a file
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_PATH);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);

        // Reading input file and storing its content into an array
        String[] inputArr = FileInput.readFile(INPUT_PATH, false, false);
        // Parsing input array to create a map of BNF key-value pairs. I Choose map bcause it is a common way to store pairs
        Map<String, String> map = FileInput.parseInput(inputArr);
        // Output the result of BNF recursion
        System.out.print("(" + BNFrecursion(map) + ")");
    }

    // Recursive function to expand BNF grammar
    public static String BNFrecursion(Map<String, String> map) {
        String temporaryString = map.get("S"); // Starting point of BNF expansion
        for (char h : temporaryString.toCharArray()) { 
            if (Character.isUpperCase(h)) { // If character is uppercase (non-terminal)
                // Replace with its corresponding expansion
                temporaryString = temporaryString.replaceFirst(("" + h), ("(" + map.get(("" + h)) + ")"));
                break;
            }
        }
        map.put("S", temporaryString); // Update the starting point with the expanded string
        // Base case: check if there are still non-terminals to expand
        for (char c : temporaryString.toCharArray()) {
            if (Character.isUpperCase(c)) { // If there's another non-terminal, recursively expand
                return BNFrecursion(map);
            }
        }
        // If all characters are lowercase (terminals), exit recursion
        return temporaryString;
    }
}
