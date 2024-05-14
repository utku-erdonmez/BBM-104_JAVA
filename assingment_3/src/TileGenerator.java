import java.util.Random;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Class for generating and managing game tiles
public class TileGenerator {
    static final int TILE_SIZE = 50; 
    static final int GRID_WIDTH = 12; // Number of tiles horizontally
    static final int GRID_HEIGHT = 16; // Number of tiles vertically
    static Tile[] tiles; // Array to store generated tiles
    
    // Images for different types of tiles
    private static final Image earthImage = new Image("/assets/underground/soil_01.png");
    private static final Image grassImage = new Image("/assets/underground/top_01.png");
    private static final Image boulderImage = new Image("/assets/underground/obstacle_01.png");
    private static final Image lavaImage = new Image("/assets/underground/lava_03.png");
    
    // Valuable images
    private static final Image diamondImage = new Image("/assets/underground/valuable_diamond.png");
    private static final Image amazoniteImage = new Image("/assets/underground/valuable_amazonite.png");
    private static final Image bronziumImage = new Image("/assets/underground/valuable_bronzium.png");
    private static final Image ironiumImage = new Image("/assets/underground/valuable_ironium.png");
    private static final Image silveriumImage = new Image("/assets/underground/valuable_silverium.png");
    private static final Image goldiumImage = new Image("/assets/underground/valuable_goldium.png");
    private static final Image platinumImage = new Image("/assets/underground/valuable_platinum.png");
    private static final Image einsteiniumImage = new Image("/assets/underground/valuable_einsteinium.png");
    private static final Image emeraldImage = new Image("/assets/underground/valuable_emerald.png");
    private static final Image rubyImage = new Image("/assets/underground/valuable_ruby.png");
    
    // Generate and return a group of tiles
    public static Group generateTiles() {
        Group tilesGroup = new Group(); // Group to hold all tiles
        Random random = new Random(); // Random generator for tile generation
        
        int countSoil = 0; // Counter for soil tiles
        int countValuable = 0; // Counter for valuable tiles
        
        // Generate tiles until enough soil and at least 3 valuable tiles are generated
        while ((countSoil < ((GRID_WIDTH * GRID_HEIGHT) / 2)) || countValuable < 3) {
            countSoil = 0; // Reset soil count
            countValuable = 0; // Reset valuable count
            tilesGroup.getChildren().clear(); // Clear existing tiles
            tiles = new Tile[GRID_WIDTH * GRID_HEIGHT]; // Initialize tiles array
            
            // Create underground and sky background rectangles
            Rectangle underground = new Rectangle(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, Color.BROWN);
            tilesGroup.getChildren().add(underground);
            Rectangle sky = new Rectangle(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * 6.4, Color.SKYBLUE);
            tilesGroup.getChildren().add(sky);
            
            // Generate top row with grass tiles
            for (int col = 0; col < GRID_WIDTH; col++) {
                ImageView tileView = new ImageView();
                Tile tile = new Tile(grassImage, 0, 0); // Grass tile
                addToTilesArray(tile); // Add tile to tiles array
                countSoil++; // Increment count of soil tiles
                // Use the image in the scene
                tileView.setTranslateX(col * TILE_SIZE);
                tileView.setTranslateY(100);
                tileView.setImage(tile.getImage());
                tilesGroup.getChildren().add(tileView);
            }
            
            // Generating other tiles
            for (int row = 3; row < GRID_HEIGHT; row++) {
                for (int col = 0; col < GRID_WIDTH; col++) {
                    ImageView tileView = new ImageView();
                    Tile tile;
                    
                    // Check if it's the bottom row, left side, or right side, if it's fill with boulders
                    if (row == GRID_HEIGHT - 1 || col == 0 || col == GRID_WIDTH - 1) {
                        tile = new Tile(boulderImage, 10, 0); // Boulder tile
                    } else {
                        // Generate random tile type
                        int tileType = random.nextInt(6); // 0: Earth, 1: Lava, 2: Valuable
                        
                        switch (tileType) {
                            case 0:
                                tile = new Tile(earthImage, 0, 0); // Earth tile
                                countSoil++; // Increment count of soil tiles
                                break;
                            case 1:
                                tile = new Tile(lavaImage, 0, 0); // Lava tile
                                break;
                            case 2:
                                // Generate a valuable tile
                                int valuableIndex = random.nextInt(10);
                                switch (valuableIndex) {
                                    case 0:
                                        tile = new Tile(diamondImage, 100, 100000); // Diamond tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 1:
                                        tile = new Tile(amazoniteImage, 120, 500000); // Amazonite tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 2:
                                        tile = new Tile(bronziumImage, 10,60); // Bronzium tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 3:
                                        tile = new Tile(ironiumImage, 10,30); // Ironium tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 4:
                                        tile = new Tile(silveriumImage, 10, 100); // Silverium tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 5:
                                        tile = new Tile(goldiumImage, 20, 250); // Goldium tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 6:
                                        tile = new Tile(platinumImage, 30, 750); // Platinum tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 7:
                                        tile = new Tile(einsteiniumImage, 40, 2000); // Einsteinium tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 8:
                                        tile = new Tile(emeraldImage, 60, 5000); // Emerald tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    case 9:
                                        tile = new Tile(rubyImage, 80,20000); // Ruby tile
                                        countValuable++; // Increment count of valuable tiles
                                        break;
                                    default:
                                        tile = new Tile(diamondImage, 100, 100000); // Default to diamond tile
                                        countValuable++; // Increment count of valuable tiles
                                }
                                break;
                            default:
                                tile = new Tile(earthImage, 0, 0); // Default to earth tile
                                countSoil++; // Increment count of soil tiles
                        }
                    }
                    
                    // Position the tile
                    tile.setXPosition(col*TILE_SIZE);
                    tile.setYPosition(row*TILE_SIZE);
                    tileView.setImage(tile.getImage());
                    addToTilesArray(tile); // Add tile to tiles array
                    tileView.setTranslateX(col * TILE_SIZE);
                    tileView.setTranslateY(row * TILE_SIZE);
                    tilesGroup.getChildren().add(tileView);
                }
            }
        }
        return tilesGroup;
    }
    
    // Add a tile to the tiles array
    public static Tile[] addToTilesArray(Tile tile) {
        if (tiles == null || tiles.length == 0) {
            tiles = new Tile[GRID_WIDTH * GRID_HEIGHT];
        }
        // Adds the tile to the first available null position in the tiles array.
        for(int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                tiles[i] = tile;
                break;
            }
        }
        return tiles;
    }
}
