abstract class Decoration {
    String name;
    String type;
    double price;

    public abstract double calculateCost(double area);
    public String getName() {
        return name;
    }
}

// Concrete class for Tile decoration
class TileDecoration extends Decoration {
    double areaPerTile;

    public TileDecoration(String name, double price, double areaPerTile) {
        this.name = name;
        this.type = "Tile";
        this.price = price;
        this.areaPerTile = areaPerTile;
    }

    @Override
    public double calculateCost(double area) {

        return (Math.ceil((area / areaPerTile)) * price);
    }
}

// Concrete class for Paint decoration
class PaintDecoration extends Decoration {
    public PaintDecoration(String name, double price) {
        this.name = name;
        this.type = "Paint";
        this.price = price;
    }

    @Override
    public double calculateCost(double area) {
        return ((area) * price);
    }
}

// Concrete class for Wallpaper decoration
class WallpaperDecoration extends Decoration {
    public WallpaperDecoration(String name, double price) {
        this.name = name;
        this.type = "Wallpaper";
        this.price = price;
    }

    @Override
    public double calculateCost(double area) {
        return (area) * price;
    }
}