import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Player class represents the miner in the game.
public class Player {
    static int xPosition; // Current x position of the player
    static int yPosition; // Current y position of the player
    static int sceneWidth; 
    static int sceneHeight; 
    static Group tilesGroup; // Group containing all tiles in the game
    static int totalWeight = 0; // Total weight of mined tiles
    static int totalValue = 0; // Total money from mined tiles
    static Text counterText; 
    static Timeline gravityTimeline; // Timeline for implementing gravity
    static ImageView playerView; // ImageView representing the player character

    public Player(int x, int y, int sceneWidth, int sceneHeight, Group tilesGroup, Text counterText) {
        xPosition = x;
        yPosition = y;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.tilesGroup = tilesGroup;
        this.counterText = counterText;

        // Initialize playerView with player image
        Image playerImage = new Image("/assets/drill/drill_23.png");
        playerView = new ImageView(playerImage);
        playerView.setFitWidth(50);
        playerView.setFitHeight(50);
        playerView.setX(xPosition);
        playerView.setY(yPosition);

        // Initialize gravity timeline and start it
        initializeGravityTimeline();
        gravityTimeline.play();
    }

    // Initialize the gravity timeline
    private void initializeGravityTimeline() {
        gravityTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(0.5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gravity();
                    }
                })
        );
        gravityTimeline.setCycleCount(Animation.INDEFINITE);
    }

    // Getter method for playerView
    public ImageView getplayerView() {
        return playerView;
    }

    // Move the player character up
    // Following this method, other control methods perform similar actions.
    public void moveUp() {
        if (yPosition >= 50) { // Prevents the player from moving beyond the game borders.
            if (checkLocation(xPosition, yPosition - 50) !=0) return; // Prevents the miner (player) from mining a block above. A return value of 0 from checkLocation indicates an empty space.
            if (checkLocationAndRemoveBlock(xPosition, yPosition - 50)) {
                // Move the player downwards after all actions are completed.
                yPosition -= 50;
                playerView.setY(yPosition);
                Image playerImage = new Image("/assets/drill/drill_23.png");// Load the image for the player's look up angle.
                playerView.setImage(playerImage);
            }
        }
    }

    // Similar functionality to MoveUp 
    public void moveDown() {
        if (yPosition + 50 + 50 <= sceneHeight) {
            if (checkLocationAndRemoveBlock(xPosition, yPosition + 50)) {
                yPosition += 50;
                playerView.setY(yPosition);
                Image playerImage = new Image("/assets/drill/drill_45.png");
                playerView.setImage(playerImage);
            }
        }
    }

    // Move the player character left
    public void moveLeft() {
        if (xPosition - 50 >= 0) {
            if (checkLocationAndRemoveBlock(xPosition - 50, yPosition)) {
                xPosition -= 50;
                playerView.setX(xPosition);
                Image playerImage = new Image("/assets/drill/drill_01.png");
                playerView.setImage(playerImage);
            }
        }
    }

    // Move the player character right
    public void moveRight() {
        if (xPosition + 50 + 50 <= sceneWidth) {
            if (checkLocationAndRemoveBlock(xPosition + 50, yPosition)) {
                xPosition += 50;
                playerView.setX(xPosition);
                Image playerImage = new Image("/assets/drill/drill_55.png");
                playerView.setImage(playerImage);
            }
        }
    }

    // Implement gravity for the player character. Unlike moving downward, this method only checks the incoming block without removing it.
    public void gravity() {
        if (checkLocation(xPosition, yPosition + 50) == 0) {
            yPosition += 50;
            playerView.setY(yPosition);
        }
    }

    /*
    Check the location for obstacles or tiles to mine and empty space.
    Works as follows: iterates over every block, and if any block's x and y coordina-
    tes match the miner's, it's considered a valid position except lava and boulder.
    */
    public static int checkLocation(int x, int y) {
        for (Node node : tilesGroup.getChildren()) { // iterate over blocks.
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                
                double tileX = imageView.getTranslateX();
                double tileY = imageView.getTranslateY();

                if (tileX == x && tileY == y) {
                    if (imageView.getImage().getUrl().trim().endsWith("obstacle_01.png") || imageView.getImage().getUrl().trim().endsWith("lava_03.png")) {
                        return 2;// lava or obstacle
                    } else {
                        return 1;// valid block to mine.
                    }
                }
            }
        }
        return 0;// Empty space
    }

   // Similar to checkLocation, but after locating the block to be mined, removes image of tile and also removes the tile object from tilAarray.
    public static boolean checkLocationAndRemoveBlock(int x, int y) {
        for (Node node : tilesGroup.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                double tileX = imageView.getTranslateX();
                double tileY = imageView.getTranslateY();

                if (tileX == x && tileY == y) {
                    if (imageView.getImage().getUrl().trim().endsWith("obstacle_01.png")) {
                        return false;
                    } else if (imageView.getImage().getUrl().trim().endsWith("lava_03.png")) {
                        gameOver();
                        return false;
                    } else {// This statement to remove image of mineable block.
                        Tile[] tileArray = TileGenerator.tiles;
                        // Finds the object in the tile array that has the same coordinates as the image.
                        for (int i = 0; i < tileArray.length; i++) {
                            try {
                                if (tileArray[i].getXPosition() == tileX && tileArray[i].getYPosition() == tileY) {
                                    totalWeight += tileArray[i].getWeight();
                                    totalValue += tileArray[i].getValue();
                                    tileArray[i] = null;
                                    break;
                                }
                            } catch (Exception e) {
                                // Since tiles are often null, I'm handling NullPointerExceptions here and just continue to iterating loop.
                                continue;
                            }
                        }
                    }
                    // Remove the tile from the tilesGroup
                    tilesGroup.getChildren().remove(imageView);
                    return true;
                }
            }
        }
        return true;
    }

    // Method to handle key presses for player movement
    public void handleKeyPress(KeyEvent event) {
        KeyCode keyPressed = event.getCode();
        Main.FUEL -= 100; // Reduce fuel

        switch (keyPressed) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            default:
                // Ignore other keys
        }
    }

    // Method to handle game over event
    private static void gameOver() {
        // Create a game over text
        Rectangle background = new Rectangle(sceneWidth, sceneHeight, Color.RED);
        Text gameOverText = new Text("Game Over!");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setX((sceneWidth - gameOverText.getLayoutBounds().getWidth()) / 2);
        gameOverText.setY((sceneHeight - gameOverText.getLayoutBounds().getHeight()) / 2);

        // Add the game over text to the root group
        Group root = (Group) playerView.getParent();
        root.getChildren().addAll(background, gameOverText);
    }
}
