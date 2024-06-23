import java.util.ArrayList;
import java.util.List;

public class Road {
    public String PointA;
    public String PointB;
    public int length;
    public int id;
    
    // Static list to store all Road instances
    private static List<Road> roadList = new ArrayList<>();

    public Road(String PointA, String PointB, int length, int id){
        this.PointA = PointA;
        this.PointB = PointB;
        this.length = length;
        this.id = id;
        // Add the new instance to the static list
        roadList.add(this);
    }

    public String getPointA(){
        return PointA;
    }

    public void setPointA(String PointA){
        this.PointA = PointA;
    }

    public String getPointB(){
        return PointB;
    }

    public void setPointB(String PointB){
        this.PointB = PointB;
    }

    public int getLength(){
        return length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    // Static method to access the road list
    public static List<Road> getRoadList(){
        return roadList;
    }
}
