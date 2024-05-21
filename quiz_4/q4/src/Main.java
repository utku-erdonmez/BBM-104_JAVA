import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        String INPUT_PATH = args[0];
        String OUTPUT_PATH = args[1];

        // Redirecting system output stream to a file
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_PATH);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);

        InventoryManagementSystem inventoryManagementSystem = new InventoryManagementSystem();
        String[] inputArr = FileInput.readFile(INPUT_PATH, false, false);
        // Iterate through the input file, send commands to switch case.
        for(String line:inputArr) {
            String[] parts = line.split("\t");
            String command = parts[0];

            switch (command) {
                case "ADD":
                addCommand(parts, inventoryManagementSystem);
                break;
            case "REMOVE":
                if(inventoryManagementSystem.searchByBarcode(parts[1])==null){
                    System.out.println("REMOVE RESULTS:");
                    System.out.println("Item is not found.");
                    System.out.println("------------------------------");
                    break;
                }
                inventoryManagementSystem.removeItem(parts[1]);
                System.out.println("REMOVE RESULTS:");
                System.out.println("Item is removed.");
                System.out.println("------------------------------");
                break;
            case "SEARCHBYBARCODE":
                Item itemByBarcode = inventoryManagementSystem.searchByBarcode(parts[1]);
                if (itemByBarcode != null) {
                    System.out.println("SEARCH RESULTS:");
                    System.out.println(itemByBarcode);
                } else {
                    System.out.println("SEARCH RESULTS:");
                    System.out.println("Item is not found.");
                }
                System.out.println("------------------------------");
                break;
            case "SEARCHBYNAME":
                List<Item> itemsByName = inventoryManagementSystem.searchByName(parts[1]);
                // Check there is a item
                if (itemsByName != null && !itemsByName.isEmpty()) {
                    System.out.println("SEARCH RESULTS:");
                    for (Item item : itemsByName) {
                        System.out.println(item);
                    }
                } else {
                    System.out.println("SEARCH RESULTS:");
                    System.out.println("Item is not found.");
                }
                System.out.println("------------------------------");
                break;
            case "DISPLAY":
                System.out.println("INVENTORY:");
                inventoryManagementSystem.displayItems();
                System.out.println("------------------------------");
                break;
            }   
        }
    
    }

    private static void addCommand(String[] parts, InventoryManagementSystem inventoryManagementSystem) {
        String itemType = parts[1];
        String name = parts[2];
        String attribute = parts[3];
        String barcode = parts[4];
        double price = Double.parseDouble(parts[5]);

        switch (itemType) {
            case "Book":
                inventoryManagementSystem.addItem(new Item.Book(name, attribute, barcode, price));
                break;
            case "Toy":
                inventoryManagementSystem.addItem(new Item.Toy(name, attribute, barcode, price));
                break;
            case "Stationery":
                inventoryManagementSystem.addItem(new Item.Stationery(name, attribute, barcode, price));
                break;
        }
    }
}
