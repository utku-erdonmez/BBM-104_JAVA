import javafx.scene.image.Image; 

public class Tile {
    private Image image;
    private int weight;
    private int value;// Worth of block.
    private int xPosition; 
    private int yPosition; 

    public Tile(Image image, int weight, int value) {
        this.image = image; 
        this.weight = weight; 
        this.value = value; 
        this.xPosition = 0;
        this.yPosition = 0; 
    }

    // Getter method to retrieve the tile's image
    public Image getImage() {
        return image;
    }

    // Getter method to retrieve the tile's weight
    public int getWeight() {
        return weight;
    }

    // Getter method to retrieve the tile's value
    public int getValue() {
        return value;
    }

    // Getter method to retrieve the tile's X-coordinate position
    public int getXPosition() {
        return xPosition;
    }

    // Getter method to retrieve the tile's Y-coordinate position
    public int getYPosition() {
        return yPosition;
    }

    // Setter method to set the tile's X-coordinate position
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    // Setter method to set the tile's Y-coordinate position
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
