import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InventoryManagementSystem {
    private Map<String, Item> itemsByBarcode; // Stores items by their barcode
    private Map<String, List<Item>> itemsByName; // Stores lists of items by their name

    public InventoryManagementSystem() {
        itemsByBarcode = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order. 
        itemsByName = new HashMap<>();
    }

    // Adds an item to the inventory
    public void addItem(Item item) {
        // Add item to the barcode map
        itemsByBarcode.put(item.getBarcode(), item);

        // Ensure there is a list for the item's name, then add the item to that list
        itemsByName.putIfAbsent(item.getName(), new ArrayList<>());
        itemsByName.get(item.getName()).add(item);
    }

    // Removes an item from the inventory by barcode
    public void removeItem(String barcode) {
        // Remove item from the barcode map
        Item item = itemsByBarcode.remove(barcode);
        
        // If the item existed, remove it from the name map as well
        if (item != null) {
            List<Item> itemsWithName = itemsByName.get(item.getName());
            itemsWithName.remove(item);

        }
    }

    // Searches for an item by barcode
    public Item searchByBarcode(String barcode) {
        return itemsByBarcode.get(barcode);
    }

    // Searches for items by name
    public List<Item> searchByName(String name) {
      
            return itemsByName.get(name);

    }

    public void displayItems() {
        // Print books
        for (Item item : itemsByBarcode.values()) {
            if (item instanceof Item.Book) {
                System.out.println(item);
            }
        }
        
        // Print toys
        for (Item item : itemsByBarcode.values()) {
            if (item instanceof Item.Toy) {
                System.out.println(item);
            }
        }
        
        // Print stationeries
        for (Item item : itemsByBarcode.values()) {
            if (item instanceof Item.Stationery) {
                System.out.println(item);
            }
        }
    }
    
}