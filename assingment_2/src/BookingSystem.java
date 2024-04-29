import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        String INPUT_PATH = "input.txt"; //args[0];
        String OUTPUT_PATH = "output.txt";//args[1];
        
        // Write prints to the output file
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_PATH);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        
        // Read input file into an array (inputFile is an array)
        String[] inputFile = FileInput.readFile(INPUT_PATH, true, false);
        
        // Function call to interpret the input file line by line
        interpreter(inputFile);
    }
    
    public static void interpreter(String[] file){
        // Initialize a HashMap to store vehicles created in the voyage
        HashMap<String, Vehicle> hashMap = new HashMap<>();
        
        for(String line:file){
            // Check if the current line is the last line in the file
           // If it is the last line, we will add a special 'Z_report' at the end
            boolean isLastLine=(file[file.length-1]==line);
            
            // Split the line by tab and store it in an array
            String[] tempLine = line.split("\t");
            
            // Create a list to store parameters of command
            List<String> tempList = new ArrayList<>();
            
            // Remove unnecessary spaces and fill the tempList
            for (String element : tempLine) {
                element=element.trim();
                // Add non-empty elements to the tempList
                if (!element.isEmpty()) {
                    tempList.add(element);
                }
            }
            
            // Create the command string
            String result="";
            for(String co : tempLine){
                result+=co+"\t";
            }
            System.out.println("COMMAND: "+result.trim());
            
            // Switch case based on the first element of the tempList
            switch (tempList.get(0)) {
                case "INIT_VOYAGE":
                    // Check if the command has correct number of parameters
                    if(tempList.size()>9||tempList.size()<7){
                        System.out.println("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
                        break;
                    }
                    // Check for existing voyage ID
                    if(hashMap.get(tempList.get(2))!=null){
                        System.out.println("ERROR: There is already a voyage with ID of "+tempList.get(2)+"!");
                        break;
                    }
                    // Check for valid ID
                    if((Integer.parseInt(tempList.get(2)))<1){
                        System.out.println("ERROR: "+tempList.get(2)+" is not a positive integer, ID of a voyage must be a positive integer!");
                        break;
                    }
                    try{
                        // Initialize a new Vehicle
                        Vehicle newVehicle = initVoyage(tempList);
                        // Add the vehicle to the HashMap if created successfully
                        if (newVehicle != null) {
                            hashMap.put(tempList.get(2), newVehicle);
                        }
                    }catch(Exception exception){
                        System.out.println("ERROR: There is already a voyage with ID of "+tempList.get(1)+"!");
                    }
                    break;
                case "SELL_TICKET":
                    // Check if the command has correct number of parameters
                    if(tempList.size()!=3){
                        System.out.println("ERROR: Erroneous usage of \"SELL_TICKET\" command!");
                        break;
                    }
                    // Check for valid ID
                    if((Integer.parseInt(tempList.get(1)))<1){
                        System.out.println("ERROR: "+tempList.get(1)+" is not a positive integer, ID of a voyage must be a positive integer!");
                        break;
                    }
                    try{
                        // Sell tickets using the Vehicle class method
                        hashMap.get(tempList.get(1)).sellTickets(tempList.get(2));
                    }catch(Exception exception){
                        System.out.println("ERROR: There is no voyage with ID of "+tempList.get(1)+"!");
                    }
                    break;
                case "CANCEL_VOYAGE":
                    // Check if the command has correct number of parameters
                    if(tempList.size()!=  2){
                        System.out.println("ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!");
                        break;
                    }
                    // Check for valid ID
                    if((Integer.parseInt(tempList.get(1)))<1){
                        System.out.println("ERROR: "+tempList.get(1)+" is not a positive integer, ID of a voyage must be a positive integer!");
                        break;
                    }
                    try{
                        // Cancel the voyage and remove it from the HashMap
                        hashMap.get(tempList.get(1)).cancel(tempList.get(1));
                        hashMap.remove(tempList.get(1));
                    }catch(Exception exception){
                        System.out.println("ERROR: There is no voyage with ID of "+tempList.get(1)+"!");
                    }
                    break;
                case "REFUND_TICKET":
                    // Check if the command has correct number of parameters
                    if(tempList.size()!=3){
                        System.out.println("ERROR: Erroneous usage of \"REFUND_TICKET\" command!");
                        break;
                    }
                    // Check for valid ID
                    if((Integer.parseInt(tempList.get(1)))<1){
                        System.out.println("ERROR: "+tempList.get(1)+" is not a positive integer, ID of a voyage must be a positive integer!");
                        break;
                    }
                    try{
                        // Refund ticket using the Vehicle class method
                        hashMap.get(tempList.get(1)).handleRefund(tempList.get(2));
                    }catch(Exception exception){
                        System.out.println("ERROR: There is no voyage with ID of "+tempList.get(1)+"!");
                    }
                    break;
                case "PRINT_VOYAGE":
                    // Check if the command has correct number of parameters
                    if(tempList.size()!=2){
                        System.out.println("ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!");
                        break;
                    }                   
                    // Check for valid ID
                    if((Integer.parseInt(tempList.get(1)))<1){
                        System.out.println("ERROR: "+tempList.get(1)+" is not a positive integer, ID of a voyage must be a positive integer!");
                        break;
                    }
                    try{
                        // Print voyage details using the Vehicle class method
                        hashMap.get(tempList.get(1)).print();
                    }catch(Exception exception){
                        System.out.println("ERROR: There is no voyage with ID of "+tempList.get(1)+"!");
                    }
                    break;
                case "Z_REPORT":
                    // Check if the command has correct number of parameters
                    if(tempList.size()!=1){
                        System.out.println("ERROR: Erroneous usage of \"Z_REPORT\" command!");
                        break;
                    }
                    // If it's the last line, skip printing Z Report
                    if(isLastLine) {
                        break;
                    }
                    // If HashMap is empty, print no voyages available
                    if(hashMap.isEmpty()){ 
                        System.out.println("Z Report:\n" + "-".repeat(16));
                        System.out.println("No Voyages Available!");
                        System.out.println("-".repeat(16)); 
                        break;
                    }
                    // Print Z Report
                    System.out.println("Z Report:\n" + "-".repeat(16));
                    // Sort vehicles by their IDs
                    ArrayList<Integer> sortKeys = new ArrayList<>();
                    for (String strKey : hashMap.keySet()) {
                        sortKeys.add(Integer.parseInt(strKey));
                    }
                    // Sort keys
                    Collections.sort(sortKeys);
                    // Print details of each vehicle
                    for (Integer key : sortKeys) {
                        try{
                            Vehicle v = hashMap.get(key+"");
                            v.print();
                            System.out.println("-".repeat(16));
                        }catch(Exception exception){
                            System.out.println("ERROR: cannot sort this key "+key);
                        }
                    }
                    break;
                default:
                    // Print error message for unknown commands
                    System.out.println("ERROR: There is no command namely "+tempList.get(0)+"!" );
                    break;
            }
        }
        
        // Print Z Report at the end
        System.out.println("Z Report:\n" + "-".repeat(16));
        ArrayList<Integer> sortKeys = new ArrayList<>();
        // If HashMap is empty, print no voyages available
        if(hashMap.isEmpty()){
            System.out.println("No Voyages Available!");
            System.out.print("-".repeat(16));
        }
        // Sort and print details of each vehicle
        for (String strKey : hashMap.keySet()) {
            sortKeys.add(Integer.parseInt(strKey));
        }
        Collections.sort(sortKeys);
        int i=0;
        for (Integer key : sortKeys) {
            Vehicle v = hashMap.get(key+"");
            v.print();
            i++;
            System.out.print("-".repeat(16));
            if(i<sortKeys.size()){
                System.out.println();
            }
        }
    }
    
    public static Vehicle initVoyage(List<String> newLine) {
        // TLDR: Parsing each newline string, followed by error checking. Upon completion, a class is instantiated.

        // Extracting vehicle type from input
        String type = newLine.get(1);
        // Extracting vehicle ID from input and parsing it to Double
        Double id = Double.parseDouble(newLine.get(2));
        // Checking if ID is a positive integer
        if (id < 1) {
            System.out.println("ERROR: " + (int)Math.floor(id) + " is not a positive integer, ID of a voyage must be a positive integer!");
            return null;
        }
        // Combining route information from input
        String route = (newLine.get(3) + "\t" + newLine.get(4));
        // Extracting number of seat rows from input and parsing it to Integer
        int rows = Integer.parseInt(newLine.get(5));
        // Checking if number of rows is a positive integer
        if (rows < 1) {
            System.out.println("ERROR: " + (int)Math.floor(rows) + " is not a positive integer, number of seat rows of a voyage must be a positive integer!");
            return null;
        }
        // Extracting price from input and parsing it to Double
        Double price = Double.parseDouble(newLine.get(6));
        // Checking if price is a positive number
        if (price < 1) {
            System.out.println("ERROR: " + (int)Math.floor(price) + " is not a positive number, price must be a positive number!");
            return null;
        }
        // Extracting refund cut from input and parsing it to Double
        Double refundCut = newLine.size() >= 8 ? Double.parseDouble(newLine.get(7)) : 1.0;
        // Checking if refund cut is within the range [0, 100]
        if (refundCut < 0 || refundCut > 100) {
            System.out.println("ERROR: " + (int)Math.floor(refundCut) + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
            return null;
        }
        // Extracting premium fee from input and parsing it to Double
        Double premiumFee = newLine.size() >= 9 ? Double.parseDouble(newLine.get(8)) : 1.0;
        // Checking if premium fee is a non-negative number
        if (premiumFee < 0 || premiumFee > 100) {
            System.out.println("ERROR: " + (int)Math.floor(premiumFee) + " is not a non-negative integer, premium fee must be a non-negative integer!");
            return null;
        }
    
        // After parsing, it's time for creating vehicle instances
        try {
            Vehicle tempVehicle;
            // Creating appropriate vehicle object based on type
            if (type.equals("Standard")) {
                tempVehicle = new StandardVehicle(type, id, route, rows, price, refundCut);
            } else if (type.equals("Premium")) {
                tempVehicle = new PremiumVehicle(type, id, route, rows, price, refundCut, premiumFee);
            } else if (type.equals("Minibus")) {
                tempVehicle = new MinibusVehicle(type, id, route, rows, price);
            } else {
                // If the type is unrecognized, throw an exception
                System.out.println("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!");
                throw new Exception();
            }
            // Returning the created vehicle object
            return tempVehicle;
        } catch (Exception exception) {
            // Catching any exceptions and returning null. Error will handled by absence of vehicle where the function used.
            return null;
        }
    }
    
}