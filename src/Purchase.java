public class Purchase {
    private String name = "";
    private int cash=0;
    private String choice = "";
    private int value = 0;

    public Purchase(String input) {
       
        String[] parts = input.split("\t");


        String[] cashStrings = parts[1].split(" ");
        for (int i = 0; i < cashStrings.length; i++) {
            this.cash += Integer.parseInt(cashStrings[i]);
        }


        
        this.choice = parts[2];
        this.value = Integer.parseInt(parts[3]);
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

    public int getValue() {
        return value;
    }
}
