import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapAnalyzer {
    
    public static void main(String[] args) throws Exception{ 
        
        String INPUT_PATH = args[0];
        String OUTPUT_PATH= args[1];
        // Redirecting system output stream to a file.
        // Main method throws an exception because of here. stream redirection requires handling
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_PATH);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
   

        // Read input file and parse input
        String[] fileInput = FileInput.readFile(INPUT_PATH, false, false);
        FileInput.parseInput(fileInput);
        
        // Find shortest road and output the result
        List<Road> roadList=Road.getRoadList();
        Map<String, String[]>  FastestRouteResultTable=FastestRoute.findTheShortestPathTable(roadList);
        FastestRoute.output(FastestRouteResultTable,"");

        // Used a variation of Prim's algorithm to find the minimum length connected path
        BarelyConnectedMap prims = new BarelyConnectedMap(roadList);
        List<Road> minimumPathList= prims.findShortestConnectedPath(FastestRoute.startPointName);
        prims.printBarelyConnectedMap(minimumPathList);

        // Find shortest path on barely connected map and output the result
        Map<String, String[]>  primsResultTable=FastestRoute.findTheShortestPathTable(minimumPathList);
        FastestRoute.output(FastestRoute.findTheShortestPathTable(minimumPathList)," on Barely Connected Map");

        // Perform analysis and print results
        analysis(FastestRouteResultTable,primsResultTable,roadList,minimumPathList);
    }
    
    // Method to perform analysis and print results
    public static void analysis( Map<String, String[]>  FastestRouteResultTable,Map<String, String[]>  primsResultTable,List<Road> roadList,List<Road> minimumPathList){
        // Calculate total input distance
        Double inputDistanceSub=0.0;
        for(Road road:roadList){
            inputDistanceSub+=(double)road.getLength();
        }

        // Calculate total distance on barely connected map
        Double barelyConnectedMapDistanceSub=0.0;
        for(Road road:minimumPathList){
            barelyConnectedMapDistanceSub+=(double)road.getLength();
        }

        // Extract shortest path length from FastestRoute's result table
        Double shortestPath=Double.parseDouble(FastestRouteResultTable.get(FastestRoute.endPointName)[0]);
        
        // Extract shortest path length 
        Double shortestBarelyConnectedPath=Double.parseDouble(primsResultTable.get(FastestRoute.endPointName)[0]);

        // Calculate ratio of construction material usage between barely connected and original map.(just compares legths)
        Double distanceRatio=barelyConnectedMapDistanceSub/inputDistanceSub;
        
        // Calculate ratio of fastest route between barely connected and original map
        Double speedRatio=shortestBarelyConnectedPath/shortestPath;
        
        // Print analysis results
        System.out.println("Analysis:");
        System.out.printf(Locale.US, "Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f%n", distanceRatio);
        System.out.printf(Locale.US, "Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", speedRatio);
    }
}
