public class Food {
    private String name = "";
    private double price = 0;
    private double protein = 0;
    private double karb = 0;
    private double fat = 0;
    private int quantity = 1; 

    public Food() {

    }

    // Constructor
    public Food(String input) {
        String[] parts = input.split("\t");

        this.name = parts[0];
        this.price = Double.parseDouble(parts[1]);

        String[] nutrientParts = parts[2].split(" ");

        this.protein = Double.parseDouble(nutrientParts[0]);
        this.karb = Double.parseDouble(nutrientParts[1]);
        this.fat = Double.parseDouble(nutrientParts[2]);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getKarb() {
        return karb;
    }

    public void setKarb(double karb) {
        this.karb = karb;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public int getQuantity() { 
        return quantity;
    }

    public void updateQuantity(int number) { 
        this.quantity += number;
    }
    public int getCalories(){
        return (int)Math.round((protein*4)+(karb*4)+(fat*9));
    }
}
