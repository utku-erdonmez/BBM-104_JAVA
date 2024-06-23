import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastestRoute{
    // Variables to store start and end point names. 
    public static String startPointName;
    public static String endPointName;

    // Method to find the shortest path table with given a list of roads.
    public static Map<String, String[]> findTheShortestPathTable( List<Road> roadList){

        Map<String, String[]> table = new HashMap<>();
        List<Road> removableRoadList = new ArrayList<>(roadList);
        List<String> usedPointsList = new ArrayList<>();
        List<String> unusedPointsList = new ArrayList<>();
        Map<String, Integer> PointIds = new HashMap<>();

        // Initialize the start point with distance 0, no previous Point, and no road ID.
        String[] initialValues = {"0", "initialPreviousPoint", "-1"};
        table.put(startPointName, initialValues);
        unusedPointsList.add(startPointName);
        PointIds.put(startPointName, -1); // Assuming the start Point has no ID (-1)

        // Populate PointIds map.
        for (Road road : removableRoadList){
        
            PointIds.putIfAbsent(road.getPointA(), road.getId());
            PointIds.putIfAbsent(road.getPointB(), road.getId());
        }

        // This loop iterates until there are no unused Points left, ensuring all possible paths are explored.
        while (!unusedPointsList.isEmpty()){
            String currPointName = findUnusedPointWithMinimumDistance(table, usedPointsList, PointIds);// Find the closest Point among the unused Points.
            // Iterate over all remaining roads to find neighbors of the current Point.
            for (int i = removableRoadList.size()-1; i>=0; i--){
                // Removing elements from a list while iterating can cause unexpected behavior
                // so iterating in reverse helps avoid issues with index shifting.
                Road road = removableRoadList.get(i);
                // Determine the other Point connected by the road
                String otherPointName =null;
                if (road.getPointA().equals(currPointName)){
                    otherPointName = road.getPointB();
                } else if (road.getPointB().equals(currPointName)){
                    otherPointName = road.getPointA();
                }
                
                if (otherPointName != null){ // This check is necessary because the "otherPointName" could be null if all corresponding Points removd.
                int previousDistanceFromStart= Integer.parseInt(table.get(currPointName)[0]);
                int newDistanceFromStart = previousDistanceFromStart+ road.getLength();
                    // If the new path explored or the new path is shorter than the existing one, update the table with the new path.
                    if (!table.containsKey(otherPointName) || newDistanceFromStart < Integer.parseInt(table.get(otherPointName)[0])){ 
                        String[] values = {String.valueOf(newDistanceFromStart), currPointName, String.valueOf(road.getId())};
                        table.put(otherPointName, values);
                        if (!unusedPointsList.contains(otherPointName)) {
                            unusedPointsList.add(otherPointName);
                        }
                    }

                    // Remove the examined road from the list of available roads
                    removableRoadList.remove(i);
                }
            }

            unusedPointsList.remove(currPointName);
            usedPointsList.add(currPointName);
        }
        return table;

    }

    public static String findUnusedPointWithMinimumDistance(Map<String, String[]> table, List<String> usedPointsList, Map<String, Integer> PointIds) {
        String unusedPointWithMinimumDistanceToStart = null; 
        int minimumDistance = Integer.MAX_VALUE;
        int minimumId = Integer.MAX_VALUE; 

        // Iterate through all available Points to find the closest one. If distances are equal, choose the one with the lesser IDç
        for (String key : table.keySet()) {
            if (!usedPointsList.contains(key)) {
                int distance = Integer.parseInt(table.get(key)[0]);// Distance is the shortest path back to the starting point, its stored in table.
                int id = PointIds.get(key);
                if (distance < minimumDistance || (distance == minimumDistance && id < minimumId)){
                    minimumDistance = distance;
                    minimumId = id;
                    unusedPointWithMinimumDistanceToStart = key;
                }
            }
        }

        return unusedPointWithMinimumDistanceToStart;
    }

    public static void output(Map<String, String[]> table,String special){
        
        String totalDistance = table.get(endPointName)[0];
        System.out.println("Fastest Route from " + startPointName + " to " + endPointName + special+" (" + totalDistance + " KM):");

        // To store the path and distances
        List<String> path = new ArrayList<>();
        String currentPoint = endPointName;

      // Traces back the path from the endPointName to the startPointName by proccessing the information stored in the table
        while (!currentPoint.equals("initialPreviousPoint")){
            path.add(0, currentPoint);  // Add the current Point at the beginning of the list
            currentPoint = table.get(currentPoint)[1];
        }

        // Print each segment of the path
        for (int i = 0; i < path.size() - 1; i++){
            String fromPoint = path.get(i);
            String toPoint = path.get(i + 1);
            String roadId = table.get(toPoint)[2];
            int distance = Integer.parseInt(table.get(toPoint)[0]) - Integer.parseInt(table.get(fromPoint)[0]);
            
            Road currRoad = null;
        
            // Iterate through the list of roads to find the one with the matching ID
            for (Road road : Road.getRoadList()) {
                if (Integer.parseInt(roadId) == road.getId()){
                    currRoad = road;
                    break; // Stop searching once the road is found
                }
            }
        

            System.out.println(currRoad.getPointA() + "\t" + currRoad.getPointB() + "\t" + distance + "\t" + roadId);
    
        }

        

    } 
    
}