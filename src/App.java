public class App {
    private static final int ROWS = 6;
    private static final int COLUMNS = 4;
    private static final int SLOT_CAPACITY = 10;
    private static Food[][] slots = new Food[ROWS][COLUMNS];

    public static void main(String[] args) throws Exception {
        String product_path = "Product_1.txt";
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
        String purchase_path = "Purchase_1.txt";
        String[] purchaseContent = FileInput.readFile(purchase_path, false, false);

        for (String line2 : purchaseContent) {
            
            Product newProduct=new Product(line2);
            System.out.println(newProduct.getName());
            System.out.println(newProduct.getCash());
            System.out.println(newProduct.getChoice());
            System.out.println(newProduct.getValue());
            System.out.println("INPUT: CASH	%d	CARB	30");
        }
    }

    public static int loadFood(Food food) {
        for(int i=0;i<ROWS;i++){
            for (int j = 0; j < COLUMNS; j++){
                
                if(slots[i][j]==null){
                    slots[i][j]=food;
                    return 1;
                } 
                if(slots[i][j].getName().equals(food.getName()) && slots[i][j].getQuantity()<10){
                    slots[i][j].updateQuantity(1);
                    return 1;
                }
                if(i==ROWS-1 && j==COLUMNS-1){
                    System.out.println(String.format("INFO: There is no available place to put %s", food.getName()));         
                    if(isMachineFull()){
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
    public static boolean isMachineFull(){
        int filledSlots=0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(slots[i][j]==null){
                    return false;
                }
                if(slots[i][j].getQuantity()==10) {
                    filledSlots+=1;
                }
            }
        }
        if (filledSlots==24) {
            return true;
        }else{
            return false;
        }
        }
   
}