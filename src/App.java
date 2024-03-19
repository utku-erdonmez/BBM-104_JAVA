public class App {
    private static final int ROWS = 6;
    private static final int COLUMNS = 4;

    private static Food[][] slots = new Food[ROWS][COLUMNS];

    public static void main(String[] args) throws Exception {
        
        String product_path = "Product_2.txt";
        String[] productContent = FileInput.readFile(product_path, false, false);

        for (String line : productContent) {
            Food newFood = new Food(line);
            int result = loadFood(newFood);
            if (result == -1) {
                System.out.println("INFO: The machine is full!");
                break; // Exit the loop if the machine is full
            }
        }
        // Print loaded food items
        printMachine();
        ///
        String purchase_path = "Purchase_2.txt";
        String[] purchaseContent = FileInput.readFile(purchase_path, false, false);

        for (String line2 : purchaseContent) {
            System.out.println("INPUT: "+line2);
            Purchase newPurchase = new Purchase(line2);// ınput line
            purchaseFood(newPurchase);

            /*
             * System.out.println(newProduct.getName());
             * System.out.println(newProduct.getCash());
             * System.out.println(newProduct.getChoice());
             * System.out.println(newProduct.getValue());
             */

        }
        printMachine();
    }

    public static int loadFood(Food food) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                if (slots[i][j] == null) {
                    slots[i][j] = food;
                    return 1;
                }
                if (slots[i][j].getName().equals(food.getName()) && slots[i][j].getQuantity() < 10) {
                    slots[i][j].updateQuantity(1);
                    return 1;
                }
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
                        slots[i][j] != null ? slots[i][j].getName() : "___",
                        slots[i][j] != null ? slots[i][j].getCalories() : "0",
                        slots[i][j] != null ? slots[i][j].getQuantity() : "0"));
            }
            System.out.print("\n");
        }
        System.out.println("----------");
    }

    public static boolean isMachineFull() {
        int filledSlots = 0;
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
        //CODE 1 FOR FOUND
        if(newPurchase.getChoice().equals("NUMBER")){
            if(newPurchase.getValue()>=24 ||newPurchase.getValue()<0){

                System.out.println("INFO: Number cannot be accepted. Please try again with another number.");
                System.out.println("RETURN: Returning your change: "+(newPurchase.getCash())+" TL");
                return -1;
            }else{
                int row=(int)(newPurchase.getValue()/4);
                int col=(int)((newPurchase.getValue())%4);
                //System.out.println(row+"d"+col);
                if(slots[row][col]==null||slots[row][col].getQuantity()<1){
                    System.out.println("This slot is empty, your money will be returned.");
                
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
                    //ELSE PARA YETMEDİ AQ
                }
            }
        }
        else{
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    switch (newPurchase.getChoice()){
                        
                        case "PROTEIN":

                            if(slots[i][j]==null) break;
                            if(slots[i][j].getProtein()+5>=newPurchase.getValue()&& slots[i][j].getProtein()-5<=newPurchase.getValue()){
               
                                if(newPurchase.getCash()>slots[i][j].getPrice()){
                                    System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                    if(slots[i][j].getQuantity()==0) break;
                                    slots[i][j].DecreaseQuantity();
                                    int remainingMoney=(int)(newPurchase.getCash()-slots[i][j].getPrice());
                                    System.out.println("RETURN: Returning your change: "+remainingMoney+" TL");
                                    return 1;   
                                }
                                else{
                                    System.out.println("INFO: Insufficient money, try again with more money.");
                                    System.out.println("RETURN: Returning your change: "+newPurchase.getCash()+" TL");
                                    return -1;   

                                }

                                  
                            }
                            break;
                                        
                            case "CARB":

                            if(slots[i][j]==null) {break;};
                            if(slots[i][j].getKarb()+5>=newPurchase.getValue()&& slots[i][j].getKarb()-5<=newPurchase.getValue()){
               
                                if(newPurchase.getCash()>slots[i][j].getPrice()){
                                    System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                    if(slots[i][j].getQuantity()==0) break;
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
                                    System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                    if(slots[i][j].getQuantity()==0) break;
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
                                    System.out.println("PURCHASE: You have bought one "+slots[i][j].getName());
                                    if(slots[i][j].getQuantity()==0) break;
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