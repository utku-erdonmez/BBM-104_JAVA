import java.util.ArrayList;
import java.util.List;

// Used a variation of Prim's algorithm to find the minimum length connected path
public class BarelyConnectedMap{
    private List<Road> roadList;

    // Constructor
    public BarelyConnectedMap(List<Road> roadList) {
        this.roadList = roadList;
    }

    // This method  discovers the shortest connected path, starting from the point with the first alphabetical word in Road list.
    public List<Road> findShortestConnectedPath(String startPoint) {
        List<Road> minimumPathList = new ArrayList<>(); 
        List<String> visited = new ArrayList<>(); // List to track visited points
        List<Road> candidateRoads = new ArrayList<>(); // List to store candidate roads for exploration

        // Add roads to the candidate list which connected to the start point 
        for (Road road : roadList) {
            if (road.getPointA().equals(startPoint) || road.getPointB().equals(startPoint)){
                candidateRoads.add(road);
            }
        }

        visited.add(startPoint); // Mark the start point as visited

        // Keep exploring until either all possible paths are proccessed or all points have been visited.
        while (!candidateRoads.isEmpty() && visited.size() < roadList.size()){
            // Find the shortest road from the candidate list
            Road shortestRoad = candidateRoads.get(0);
            for (Road road : candidateRoads){
                // Update the shortestRoad variable if the current road is shorter than the current shortest road, or if their lengths are equal but the current road has a lower ID.
                if (road.getLength() < shortestRoad.getLength() || (road.getLength() == shortestRoad.getLength() && road.getId() < shortestRoad.getId())){
                    shortestRoad = road;
                }
            }

            candidateRoads.remove(shortestRoad); // Remove the shortest road from candidate list. We will use shortest road in below just removed from list.

            // Determine the next point to visit based on the shortest road
            String nextPoint = visited.contains(shortestRoad.getPointA()) ? shortestRoad.getPointB() : shortestRoad.getPointA();

            // If the next point is not visited yet
            if (!visited.contains(nextPoint)){
                visited.add(nextPoint); // Mark the next point as visited
                minimumPathList.add(shortestRoad); // Add the shortest road to the minimum path

                // Add roads connected to the newly visited point to the candidate list
                for (Road road : roadList){
                    if ((road.getPointA().equals(nextPoint) || road.getPointB().equals(nextPoint)) && !minimumPathList.contains(road)){
                        candidateRoads.add(road);
                    }
                }
            }
        }

        sortRoads(minimumPathList); // Sort the minimum path by road length and ID

        return minimumPathList; // Return the minimum path
    }

    // Method to sort roads by length and ID
    public static void sortRoads(List<Road> roads){
        int numberOfRoads = roads.size();
        // Iterate through each road except the last one
        for (int firstRoadIndex = 0; firstRoadIndex < numberOfRoads - 1; firstRoadIndex++){
            // Iterate through roads after the current road
            for (int secondRoadIndex = firstRoadIndex + 1; secondRoadIndex < numberOfRoads; secondRoadIndex++){
                // Get the first and second roads for comparison
                Road firstRoad = roads.get(firstRoadIndex);
                Road secondRoad = roads.get(secondRoadIndex);
    
                // Compare roads by length and ID
                if (firstRoad.getLength() > secondRoad.getLength() || 
                    (firstRoad.getLength() == secondRoad.getLength() && firstRoad.getId() > secondRoad.getId())){
                    // Swap firstRoad and secondRoad
                    roads.set(firstRoadIndex, secondRoad);
                    roads.set(secondRoadIndex, firstRoad);
                }
            }
        }
    }

    

    // Method to print the minimumPathList
    public void printBarelyConnectedMap(List<Road> minimumPathList){
        System.out.println("Roads of Barely Connected Map is:");
        for (Road road : minimumPathList){
            System.out.println(road.getPointA() + "\t" + road.getPointB() + "\t" + road.getLength() + "\t" + road.getId());
        }
    }
}
