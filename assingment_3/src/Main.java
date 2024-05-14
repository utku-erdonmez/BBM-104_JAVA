import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    // Define window dimensions
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    // Initial fuel value
    public static double FUEL = 9999.90;
    // Timeline for updating game state
    private Timeline timeline;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Assingment 3");
        // Create text object for displaying game stats
        Text counterText = new Text();
        // Setting counter properties
        counterText.setFill(Color.WHITE); 
        counterText.setFont(Font.font("Arial", 30));
        counterText.setText("Fuel: " + FUEL + "\nHaul: " + 0 + "\nMoney: " + 0);
        counterText.setX(0); 
        counterText.setY(30); 
        
        // Generate tiles using TileGenerator
        Group tilesGroup = TileGenerator.generateTiles();
        // Create player object
        Player player = new Player(550, 50, 600, 800, tilesGroup, counterText);
        // Group tiles, player view, and counter text to show them together.
        Group finalGroup = new Group(tilesGroup, player.getplayerView(), counterText);

        // Create root StackPane and add the group to it
        StackPane root = new StackPane(finalGroup);

        // Create the scene and set it to the primaryStage
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Handle key presses
        scene.setOnKeyPressed(event -> player.handleKeyPress(event));
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize timeline for game loop
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            // Update fuel
            FUEL -= 0.15;
            // Update counter text
            counterText.setText("Fuel: " + String.format("%.3f", FUEL) + "\nHaul: " + Player.totalWeight + "\nMoney: " + Player.totalValue);
            // Check if fuel is down
            if (FUEL <= 0) {
                // Display gameover screen
                displayGameOver(primaryStage);
                // Stop the timeline when fuel reaches 0. that not necessary actually
                timeline.stop();
            }
        }));
        // Set timeline to repeat indefinitely
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    private void displayGameOver(Stage primaryStage) {
        // Create red rectangle and gameover text.
        Rectangle gameOverScreen = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);
        Text gameOverText = new Text("     Game Over\n collected money: " + Player.totalValue);
        gameOverText.setFont(Font.font("Arial", 50));
        gameOverText.setFill(Color.WHITE);

        // StackPane for overlaying rectangle and text
        StackPane gameOverPane = new StackPane(gameOverScreen, gameOverText);
        Scene gameOverScene = new Scene(gameOverPane);
        
        // Set the scene to the primaryStage
        primaryStage.setScene(gameOverScene);
        // Show the primaryStage
        primaryStage.show();
    }
}
