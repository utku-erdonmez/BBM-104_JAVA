import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Main class
public class Main {
    public static void main(String[] args) throws Exception {
        // Inputs
        String ITEMS_PATH = args[0];
        String DECORATION_PATH = args[1];
        String OUTPUT_PATH = args[2];
        
        // Read items and decoration files
        String[] itemsFile = FileInput.readFile(ITEMS_PATH, false, false);
        String[] decorationFile = FileInput.readFile(DECORATION_PATH, false, false);
        
        // Redirect output to a file
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_PATH);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        
        // Lists for storing classrooms and decorations
        List<Classroom> classrooms = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();
        
        // Loop through each item in the items file and create objects accordingly
        for (String item : itemsFile) {
            // Split each line of items file into parts
            String[] itemsArr = item.split("\t");
            // Check if the item is a classroom or a decoration and create objects accordingly
            if (itemsArr[0].equals("CLASSROOM")) {
                // Create Classroom object
                Classroom newClassroom = new ClassroomBuilder()
                        .setName(itemsArr[1])
                        .setShape(itemsArr[2])
                        .setWidth(Double.parseDouble(itemsArr[3]))
                        .setLength(Double.parseDouble(itemsArr[4]))
                        .setHeight(Double.parseDouble(itemsArr[5]))
                        .build();
                classrooms.add(newClassroom);
            }

            if (itemsArr[0].equals("DECORATION")) {
                // Create Decoration objects
                if (itemsArr[2].equals("Wallpaper")) {
                    WallpaperDecoration wallpaperDecoration = new WallpaperDecoration(itemsArr[1], Double.parseDouble(itemsArr[3]));
                    decorations.add(wallpaperDecoration);
                }
                if (itemsArr[2].equals("Paint")) {
                    PaintDecoration paintDecoration = new PaintDecoration(itemsArr[1], Double.parseDouble(itemsArr[3]));
                    decorations.add(paintDecoration);
                }
                if (itemsArr[2].equals("Tile")) {
                    TileDecoration tileDecoration = new TileDecoration(itemsArr[1], Double.parseDouble(itemsArr[3]), Double.parseDouble(itemsArr[4]));
                    decorations.add(tileDecoration);
                }
            }
        }

        // Total cost
        int TOTAL = 0;
        
        // Loop through each line in the decoration file and calculate costs
        for (String line : decorationFile) {
            // Split each line of decoration file into parts
            String[] parts = line.split("\t");
            // Extract relevant information for calculation
            String currClassroom = parts[0];
            String currWall = parts[1];
            String currFloor = parts[2];
            double currFloorArea = 0.0;
            double currWallArea = 0.0;
            double wallCost = 0.0;
            double floorCost = 0.0;

            // Calculate areas for current classroom
            for (Classroom classroom : classrooms) {
                if (classroom.getName().equals(currClassroom)) {
                    currFloorArea = classroom.calculateFloorArea();
                    currWallArea = classroom.calculateWallArea();
                }
            }

            // Calculate costs and generate messages for walls and floors
            String messageFloor = "";
            String messageWall = "";
            for (Decoration decoration : decorations) {
                // Calculate cost for walls
                if (decoration.getName().equals(currWall)) {
                    wallCost = decoration.calculateCost(currWallArea);
                    if (decoration.type == "Paint" || decoration.type == "Wallpaper") {
                        messageWall = ("used " + (int) Math.ceil(currWallArea) + "m2" + " of " + decoration.type + " for walls");
                    }
                    if (decoration.type == "Tile") {
                        messageWall = ("used " + (int) Math.ceil(decoration.calculateCost(currWallArea) / decoration.price) + " Tiles for walls");
                    }
                }
                // Calculate cost for floors
                if (decoration.getName().equals(currFloor)) {
                    floorCost = decoration.calculateCost(currFloorArea);
                    if (decoration.type == "Paint" || decoration.type == "Wallpaper") {
                        messageWall = ("used " + (int) Math.ceil(currFloorArea) + "m2" + " of " + decoration.type + " for flooring");
                    }
                    if (decoration.type == "Tile") {
                        messageFloor = ("used " + (int) Math.ceil(decoration.calculateCost(currFloorArea) / decoration.price) + " Tiles for flooring");
                    }
                }
            }
            // Output cost message for the current classroom
            System.out.println("Classroom " + currClassroom + " " + messageWall + " and " + messageFloor + ", these costed " + (int) Math.ceil(wallCost + floorCost) + "TL.");
            TOTAL += (int) Math.ceil(wallCost + floorCost);
        }
        // Output total price
        System.out.print("Total price is: " + TOTAL + "TL.");
    }
}
