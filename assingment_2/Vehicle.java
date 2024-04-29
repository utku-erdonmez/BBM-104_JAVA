import java.util.Locale;

abstract class Vehicle {
    // Define private attributes
    private String type;        // Type of the vehicle (Standard, Minibus, Premium)
    Double id;                 
    String route;               
    Double price;               // Price of each seat in the vehicle
    Double revenue;           
    String[][] shape;           // 2D array representing the seating arrangement of the vehicle
    int columns;                // Number of columns in the seating arrangement
    int rows;                   // Number of rows in the seating arrangement
    
    // Constructor
    public Vehicle(String type, Double id, String route, int rows, Double price) {
        // Initialize attributes
        this.type = type;               
        this.id = id;                   
        this.route = route;    
        this.price = price;             
        this.revenue = 0.0;             
        this.rows = rows;               
        
        // Determine the number of columns based on the type of vehicle
        if (this.type.equals("Standard")) 
            this.columns = 4;
        else if (this.type.equals("Minibus"))
            this.columns = 2;
        else if (this.type.equals("Premium"))
            this.columns = 3;
        
        // Create the seating arrangement matrix and initialize all seats as available ('*')
        this.shape = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.shape[i][j] = "*";   
            }
        }
    }
    public void print(){
        // Print voyage ID
        System.out.println("Voyage " + (int)this.id.doubleValue());
        
        // Print route information
        System.out.println(String.join("-", this.route.split("\t")));
        
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape[0].length; j++){
                // To prevent trailing space while printing, the last element should not have a space
                System.out.print(shape[i][j] + (j < ("Standard".equals(this.type) ? 3 : "Premium".equals(this.type) ? 2 : 1) ? " " : ""));
                
                // Add a separator for needing places
                if(j == ("Standard".equals(this.type) ? 1 : "Premium".equals(this.type) ? 0 : -1)){
                    System.out.print("| ");
                }
            }
            System.out.println(); // Move to the next line after printing each row
        }
        
        // Print revenue with US locale formatting. (US locale is important bcause prints 1,0 instead 1.0)
        System.out.println("Revenue: " + String.format(Locale.US,"%.2f", this.revenue));
    }
    
    public void cancel(String id){
        // Print a message indicating successful cancellation and voyage details
        System.out.println("Voyage " + id + " was successfully cancelled!\nVoyage details can be found below:");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Check if the seat is canceled (marked with "X")
                if (this.shape[i][j] == "X"){
                    // If the seat is canceled, decrease the revenue by the price of one seat
                    this.revenue -= this.price;
                }
            }
        }
        
        // Print the updated voyage details
        this.print();
    }
    
    public void sellTickets(String seats){
        // Using temporary variables if an error occurs, class variables won't change
        Double tempRevenue = 0.0;
        String[][] tempShape = cloneShape(); // Assuming cloneShape() returns a copy of some 2D array
        try{
            // Spliting input to parse string. Then iterate
            for(String seatNum : seats.split("_")){
                // Check if seat number is a positive integer
                if(Integer.parseInt(seatNum) < 1){
                    System.out.println("ERROR: "+seatNum+" is not a positive integer, seat number must be a positive integer!");
                    return;
                }
                // Calculate seat index based on seat number
                int seatIndex = Integer.parseInt(seatNum) - 1; 
                int seatRow = seatIndex / this.columns; 
                int seatCol = seatIndex % this.columns;
                tempRevenue += this.price; // Add revenue
                try{
                    // Check if seat is already sold
                    if(tempShape[seatRow][seatCol] == "X"){
                        System.out.println("ERROR: One or more seats already sold!");
                        return;
                    }
                    tempShape[seatRow][seatCol] = "X"; // Mark seat as sold
                } catch(Exception exception){
                    System.out.println("ERROR: There is no such a seat!");
                    return;
                }
            }
            // Create output message
            String msg = String.format(Locale.US,"Seat %s of the Voyage %.0f from %s to %s was successfully sold for %.2f TL.",
                String.join("-", seats.split("_")),
                id, 
                route.split("\t")[0], 
                route.split("\t")[1],
                tempRevenue
            );
            System.out.println(msg);
            // Update class variables
            this.shape = tempShape;
            this.revenue += tempRevenue;
        } catch(Exception IndexOutOfBoundsException){
            System.out.println("ERROR: Erroneous ROUTE!");
        }
    }
    
    
    // Method to clone a 2D array of strings
    public String[][] cloneShape(){
        // Create a new 2D array with the same dimensions as 'shape'
        String[][] tempShape = new String[this.shape.length][];
        
        for (int i = 0; i < this.shape.length; i++) {
            // Clone each row and assign it to the corresponding row in tempShape
            tempShape[i] = this.shape[i].clone();
        }
        
        // Return the cloned 2D array
        return tempShape;
    }  
    //
    abstract void handleRefund(String seats);

}
// STANDARD
class StandardVehicle extends Vehicle {
    Double refundCut; // Refund percentage

    // Constructor
    public StandardVehicle(String type, Double id, String route, int rows, Double price, Double refundCut) {
        // Call constructor of superclass (Vehicle)
        super(type, id, route, rows, price);
        // Initialize refundCut
        this.refundCut = refundCut;

        // Initial message about the initialized voyage
        String initialMessage = String.format(Locale.US,
                "Voyage %.0f was initialized as a standard (2+2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that refunds will be %.0f%% less than the paid amount.",
                id,
                route.split("\t")[0], // Origin
                route.split("\t")[1], // Destination
                price,
                rows * 4, // Total number of seats (2 seats per row, 2 columns)
                refundCut // Refund cut percentage
        );
        // Print the initial message
        System.out.println(initialMessage);
    }

    // Method to handle refunds
    @Override
    public void handleRefund(String seats) {
        try {
            Double tempRevenue = 0.0; 
            String[][] tempShape = cloneShape(); 

            // Iterate over each seat to be refunded
            for (String seatNum : seats.split("_")) {
                // Check if seat number is positive
                if (Integer.parseInt(seatNum) < 1) {
                    System.out.println("ERROR: " + seatNum + " is not a positive integer, seat number must be a positive integer!");
                    return;
                }
                // Find rows and cols
                int seatIndex = Integer.parseInt(seatNum) - 1; // Seat index
                int seatRow = seatIndex / this.columns; 
                int seatCol = seatIndex % this.columns; 
                // Calculate refund amount for the seat and add to total revenue
                tempRevenue += (this.price - this.price * this.refundCut / 100);
                try {
                    // Check if the seat is already empty
                    if (tempShape[seatRow][seatCol] == "*") {
                        System.out.println("ERROR: One or more seats are already empty!");
                        return;
                    }
                    // Mark the seat as empty
                    tempShape[seatRow][seatCol] = "*";
                } catch (Exception IndexOutOfBoundsException) {
                    System.out.println("ERROR: There is no such a seat!");
                    return;
                }
            }
            // Message for successful refund
            String msg = String.format(Locale.US,
                    "Seat %s of the Voyage %.0f from %s to %s was successfully refunded for %.2f TL.",
                    String.join("-", seats.split("_")), // Refunded seat numbers
                    id,
                    route.split("\t")[0], // Origin
                    route.split("\t")[1], // Destination
                    tempRevenue // Refund amount
            );
            // Print the refund message
            System.out.println(msg);
            // Deduct the refunded amount from total revenue
            this.revenue -= tempRevenue;
            // Update the seating arrangement
            this.shape = tempShape;
        } catch (Exception IndexOutOfBoundsException) {
            System.out.println("ERROR: Erroneous ROUTE!");
        }
    }
}

// MINIBUS 
class MinibusVehicle extends Vehicle{
    // Constructor
    public MinibusVehicle(String type, Double id, String route, int rows, Double price) {
        // Call the constructor of the superclass (Vehicle) with the provided parameters
        super(type, id, route, rows, price);
        // Create an initial message string using the provided information
        String initialMessage = String.format(Locale.US,
            "Voyage %.0f was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that minibus tickets are not refundable.",
            id,
            route.split("\t")[0], // Extract the start location from the route string
            route.split("\t")[1], // Extract the end location from the route string
            price,
            rows * 2 // Calculate the total number of seats (double rows for minibus)
        );
        // Print the initial message
        System.out.println(initialMessage);
    }
    @Override//
    // Method to handle refund requests for MinibusVehicle
    public void handleRefund(String seats){
        // Print an error message indicating that Minibus tickets are not refundable
        System.out.println("ERROR: Minibus tickets are not refundable!");
    }
}

// Premium
class PremiumVehicle extends Vehicle {
    // Attributes specific to PremiumVehicle
    Double refundCut;
    Double premiumCost;
    Double premiumPrice = 0.0;

    // Constructor for PremiumVehicle
    public PremiumVehicle(String type, Double id, String route, int rows, Double price, Double refundCut, Double premiumCost) {
        // Call the constructor of the superclass (Vehicle)
        super(type, id, route, rows, price);
        
        // Initialize PremiumVehicle-specific attributes
        this.refundCut = refundCut;
        this.premiumCost = premiumCost;
        this.premiumPrice = (price * premiumCost / 100) + price;

        // Print initial message about the PremiumVehicle
        String initialMessage = String.format(Locale.US, "Voyage %.0f was initialized as a premium (1+2) voyage from %s to %s with %.2f TL priced %d regular seats and %.2f TL priced %d premium seats. Note that refunds will be %.0f%% less than the paid amount.",
                id,
                route.split("\t")[0],
                route.split("\t")[1],
                price,
                rows * 2,
                premiumPrice,
                rows,
                refundCut
        );
        System.out.println(initialMessage);
    }

    // Method to sell tickets
    @Override
    public void sellTickets(String seats) {
        Double tempRevenue = 0.0;
        String[][] tempShape = cloneShape();

        try {
            for (String seatNum : seats.split("_")) {
                // Check if the seat number is positive
                if (Integer.parseInt(seatNum) < 1) {
                    System.out.println("ERROR: " + seatNum + " is not a positive integer, seat number must be a positive integer!");
                    return;
                }
                int seatIndex = Integer.parseInt(seatNum) - 1;
                int seatRow = seatIndex / this.columns;
                int seatCol = seatIndex % this.columns;
                // Calculate revenue based on seat type (regular or premium)
                if (seatCol == 0) {
                    tempRevenue += this.premiumPrice;
                } else {
                    tempRevenue += this.price;
                }
                try {
                    // Check if the seat is already sold
                    if (tempShape[seatRow][seatCol] == "X") {
                        System.out.println("ERROR: One or more seats already sold!");
                        return;
                    }
                    tempShape[seatRow][seatCol] = "X";
                } catch (Exception IndexOutOfBoundsException) {
                    System.out.println("ERROR: There is no such a seat!");
                    return;
                }
            }
            // Print successful sale message
            String msg = String.format(Locale.US, "Seat %s of the Voyage %.0f from %s to %s was successfully sold for %.2f TL.",
                    String.join("-", seats.split("_")),
                    id,
                    route.split("\t")[0],
                    route.split("\t")[1],
                    tempRevenue
            );
            System.out.println(msg);
            this.shape = tempShape;
            this.revenue += tempRevenue;
        } catch (Exception IndexOutOfBoundsException) {
            System.out.println("ERROR: Erroneous ROUTE!");
        }
    }

    // Method to handle refunds
    @Override
    public void handleRefund(String seats) {
        Double tempRevenue = 0.0;
        String[][] tempShape = cloneShape();

        try {
            for (String seatNum : seats.split("_")) {
                if (Integer.parseInt(seatNum) < 1) {
                    System.out.println("ERROR: " + seatNum + " is not a positive integer, seat number must be a positive integer!");
                    return;
                }
                int seatIndex = Integer.parseInt(seatNum) - 1;
                int seatRow = seatIndex / this.columns;
                int seatCol = seatIndex % this.columns;
                // Calculate refund amount based on seat type (regular or premium)
                if (seatCol == 0) {
                    tempRevenue += (this.premiumPrice - this.premiumPrice * this.refundCut / 100);
                } else {
                    tempRevenue += (this.price - this.price * this.refundCut / 100);
                }
                try {
                    // Check if the seat is already empty
                    if (tempShape[seatRow][seatCol] == "*") {
                        System.out.println("ERROR: One or more seats are already empty!");
                        return;
                    }
                    tempShape[seatRow][seatCol] = "*";
                } catch (Exception IndexOutOfBoundsException) {
                    System.out.println("ERROR: There is no such a seat!");
                    return;
                }
            }
            // Print successful refund message
            String msg = String.format(Locale.US, "Seat %s of the Voyage %.0f from %s to %s was successfully refunded for %.2f TL.",
                    String.join("-", seats.split("_")),
                    id,
                    route.split("\t")[0],
                    route.split("\t")[1],
                    tempRevenue
            );
            System.out.println(msg);
            this.shape = tempShape;
            this.revenue -= tempRevenue;
        } catch (Exception IndexOutOfBoundsException) {
            System.out.println("ERROR: Erroneous ROUTE!");
        }
    }

    // Method to cancel the voyage
    @Override
    public void cancel(String id) {
        System.out.println("Voyage " + id + " was successfully cancelled!\nVoyage details can be found below:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Adjust revenue based on cancelled seats
                if (this.shape[i][j] == "X") {
                    if (j == 0) {
                        this.revenue -= this.premiumPrice;
                    } else {
                        this.revenue -= this.price;
                    }
                }
            }
        }
        // Print details of the cancelled voyage
        this.print();
        // Reset revenue to zero
        //this.revenue = 0.0;
    }
}
