public abstract class Item {
    private String name;
    private String barcode;
    private double price;
    public Item(String name, String barcode, double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }
    // Getter and Setters.
    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    // Needed for print functions.
    @Override
    public String toString() {
        return "Its barcode is "+barcode+" and its price is "+price;
    }

    public static class Book extends Item {
        private String author;

        public Book(String name, String author, String barcode, double price) {
            super(name, barcode, price);
            this.author = author;
        }
        // Getter and Setters.
        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return "Author of the "+this.getName()+" is "+this.getAuthor()+". "+super.toString();

        }
    }

    public static class Toy extends Item {
        private String color;
        public Toy(String name, String color, String barcode, double price) {
            super(name, barcode, price);
            this.color = color;
        }

        // Getter and Setters.
        public String getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "Color of the "+this.getName()+" is "+this.getColor()+". "+super.toString();

        }
    }

    public static class Stationery extends Item {
        private String type;

        public Stationery(String name, String type, String barcode, double price) {
            super(name, barcode, price);
            this.type = type;
        }

        public String gettype() {
            return type;
        }

        @Override
        public String toString() {
            return "type of the "+this.getName()+" is "+this.gettype()+". "+super.toString();
        }
    }
}
