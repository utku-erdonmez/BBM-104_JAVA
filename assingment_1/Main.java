import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    //globals
    private static final int ROWS = 6;
    private static final int COLUMNS = 4;
    private static Food[][] slots = new Food[ROWS][COLUMNS];
    public static void main(String[] args) throws Exception {
        //paths
        String product_path = args[0];
        String purchase_path = args[1];
        String outputPath = args[2];
        // This part redirects system print stream into output stream so I can output my prints
        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        // Read products line by line and send them to loadfood function
        String[] productContent = FileInput.readFile(product_path, false, false);
        for (String line : productContent) {
            Food newFood = new Food(line);
            int result = loadFood(newFood);
            if (result == -1) {
                System.out.println("INFO: The machine is full!");
                break; 
            }
        }
        // After machine is loaded, print
        printMachine();
       // Same as load machine, read content and send them to purchase function. Finally print last state 
       //same as load machine read content and send them to purchase function. finally print last state
        String[] purchaseContent = FileInput.readFile(purchase_path, false, false);
        for (String line2 : purchaseContent) {
            System.out.println("INPUT: "+line2);
            Purchase newPurchase = new Purchase(line2);
            purchaseFood(newPurchase);
        }
        printMachine();
    }
    public static int loadFood(Food food) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                // If slot is empty, fill with this food
                if (slots[i][j] == null) {
                    slots[i][j] = food;
                    return 1;
                }
                // If slot has content but has some space for new food with same name, use here
                if (slots[i][j].getName().equals(food.getName()) && slots[i][j].getQuantity() < 10) {
                    slots[i][j].updateQuantity(1);
                    return 1;
                }
                // If loop can't find available slot for this food, use here
                if (i == ROWS - 1 && j == COLUMNS - 1) {
                    System.out.println(String.format("INFO: There is no available place to put %s", food.getName()));
                    if (isMachineFull()) {
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
    public static void printMachine() {
        System.out.println("-----Gym Meal Machine-----");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(String.format("%s(%s, %s)___",
                        (slots[i][j] != null && slots[i][j].getQuantity()!=0 )? slots[i][j].getName() : "___",
                        // If slot is empty, print its name as ____
                        slots[i][j] != null && slots[i][j].getQuantity()!=0? slots[i][j].getCalories() : "0",
                        slots[i][j] != null ? slots[i][j].getQuantity() : "0"));
            }
            System.out.print("\n");
        }
        System.out.println("----------");
    }
    public static boolean isMachineFull() {
        int filledSlots = 0;
        // Lookup all slots. If you can't find empty slot, return false
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (slots[i][j] == null) {
                    return false;
                }
                if (slots[i][j].getQuantity() == 10) {
                    filledSlots += 1;
                }
            }
        }
        if (filledSlots == 24) {
            return true;
        } else {
            return false;
        }
    }
    public static int purchaseFood(Purchase newPurchase){
        //" Number" goes to if block,other goes else
        if(newPurchase.getChoice().equals("NUMBER")){
             // Check constraints of machine
            if(newPurchase.getValue()>=24 ||newPurchase.getValue()<0){

                System.out.println("INFO: Number cannot be accepted. Please try again with another number.");
                System.out.println("RETURN: Returning your change: "+(newPurchase.getCash())+" TL");
                return -1;
            }else{
                int row=(int)(newPurchase.getValue()/4);
                int col=(int)((newPurchase.getValue())%4);
                // If slot empty or out of food return these messages
                if(slots[row][col]==null||slots[row][col].getQuantity()<1){
                    System.out.println("INFO: This slot is empty, your money will be returned.");
                
                    System.out.println("RETURN: Returning your change: "+(newPurchase.getCash())+" TL");
                    return -1;
                }
                else{
                    if(newPurchase.getCash()>slots[row][col].getPrice()){
                        System.out.println("PURCHASE: You have bought one "+slots[row][col].getName());
                        slots[row][col].DecreaseQuantity();
                        int remainingMoney=(int)(newPurchase.getCash()-slots[row][col].getPrice());
                        System.out.println("RETURN: Returning your change: "+remainingMoney+" TL");
                    }
                    else{
                        System.out.println("INFO: Insufficient money, try again with more money.");
                        System.out.println("RETURN: Returning your change: "+newPurchase.getCash()+" TL");
                        return -1;   
                    }
                }
            }
        }
        else{
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    // Switch case
                    switch (newPurchase.getChoice()){
                        case "PROTEIN":
                            if(slots[i][j]==null) break;
                            // Check if product is within +-5 range from requested value
                            if(slots[i][j].getProtein()+5>=newPurchase.getValue()&& slots[i][j].getProtein()-5<=newPurchase.getValue()){
                                // Check is cash enough to buy food
                                if(newPurchase.getCash()>slots[i][j].getPrice()){
                                    if(slots[i][j].getQuantity()==0) break;
                                    System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                    slots[i][j].DecreaseQuantity();
                                    int remainingMoney=(int)(newPurchase.getCash()-slots[i][j].getPrice());
                                    System.out.println("RETURN: Returning your change: "+remainingMoney+" TL");
                                    return 1;   
                                }
                                // Insufficient money
                                else{
                                    System.out.println("INFO: Insufficient money, try again with more money.");
                                    System.out.println("RETURN: Returning your change: "+newPurchase.getCash()+" TL");
                                    return -1;   
                                }
                            }
                            break;
                            // Every case below protein has similar content     
                            case "CARB":
                                if(slots[i][j]==null) {break;};
                                if(slots[i][j].getKarb()+5>=newPurchase.getValue()&& slots[i][j].getKarb()-5<=newPurchase.getValue()){
                                    if(newPurchase.getCash()>slots[i][j].getPrice()){
                                        if(slots[i][j].getQuantity()==0) break;
                                        System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                        slots[i][j].DecreaseQuantity();
                                        int remainingMoney=(int)(newPurchase.getCash()-slots[i][j].getPrice());
                                        System.out.println("RETURN: Returning your change: "+(remainingMoney)+" TL");
                                    }
                                    else{
                                        System.out.println("INFO: Insufficient money, try again with more money.");
                                        System.out.println("RETURN: Returning your change: "+newPurchase.getCash()+" TL");
                                    }
                                    return 1;     
                                }
                            break;
                            case "FAT":
                                if(slots[i][j]==null) {break;};
                                if(slots[i][j].getFat()+5>=newPurchase.getValue()&& slots[i][j].getFat()-5<=newPurchase.getValue()){
                
                                    if(newPurchase.getCash()>slots[i][j].getPrice()){
                                        if(slots[i][j].getQuantity()==0) break;
                                        System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                        slots[i][j].DecreaseQuantity();
                                        int remainingMoney=(int)(newPurchase.getCash()-slots[i][j].getPrice());
                                        System.out.println("RETURN: Returning your change: "+(remainingMoney)+" TL");
                                    }
                                    else{
                                        System.out.println("INFO: Insufficient money, try again with more money.");
                                        System.out.println("RETURN: Returning your change: "+newPurchase.getCash()+" TL");
                                    }
                                    return 1;     
                                }
                            break;
                            case "CALORIE":
                                if(slots[i][j]==null) {break;};
                                if(slots[i][j].getCalories()+5>=newPurchase.getValue()&& slots[i][j].getCalories()-5<=newPurchase.getValue()){
                                    if(newPurchase.getCash()>slots[i][j].getPrice()){
                                        if(slots[i][j].getQuantity()==0) break;
                                        System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                        slots[i][j].DecreaseQuantity();
                                        int remainingMoney=(int)(newPurchase.getCash()-slots[i][j].getPrice());
                                        System.out.println("RETURN: Returning your change: "+remainingMoney+" TL");
                                    }
                                    else{
                                        System.out.println("INFO: Insufficient money, try again with more money.");
                                        System.out.println("RETURN: Returning your change: "+newPurchase.getCash()+" TL");
                                    }
                                    return 1;     
                                }
                            break;
                        default:
                            break;
                    }
                }
        }
        System.out.println("INFO: Product not found, your money will be returned.\nRETURN: Returning your change: "+newPurchase.getCash()+" TL");

    }
        return 0;
}
}