public class Product {
    private String name = "";
    private int cash=0;
    private String choice = "";
    private double value = 0;

    public Product(String input) {
        String[] parts = input.split("\t");

        this.name = parts[0];

        String[] cashStrings = parts[1].split(" ");
        for (int i = 0; i < cashStrings.length; i++) {
            this.cash += Integer.parseInt(cashStrings[i]);
        }


        
        this.choice = parts[2];
        this.value = Double.parseDouble(parts[3]);
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getCash() {
        return cash;
    }

    public String getChoice() {
        return choice;
    }

    public double getValue() {
        return value;
    }
}
