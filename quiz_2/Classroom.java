// Abstract class for classroom
abstract class Classroom {
    String name;
    String shape;
    double width;
    double length;
    double height;

    public abstract double calculateWallArea();

    public abstract double calculateFloorArea();

    public String getName() {
        return name;
    }
}

// Concrete class for Circular classroom
class CircularClassroom extends Classroom {
    public CircularClassroom(String name, double width, double length,double height) {
        this.name = name;
        this.shape = "Circle";
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateWallArea() {
        return Math.PI  * width*height;
    }

    @Override
    public double calculateFloorArea() {
        return Math.PI * Math.pow(width / 2, 2);
    }
}

// Concrete class for Rectangular classroom
class RectangularClassroom extends Classroom {
    double length;

    public RectangularClassroom(String name, double width, double length, double height) {
        this.name = name;
        this.shape = "Rectangular";
        this.width = width;
        this.length = length;
        this.height = height;
    }

    @Override
    public double calculateWallArea() {
        return 2 * (width +length)*height;
    }

    @Override
    public double calculateFloorArea() {
        return width * length;
    }
}

// Builder class for building classrooms
class ClassroomBuilder {
    String name;
    String shape;
    double width;
    double length;
    double height;

    public ClassroomBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ClassroomBuilder setShape(String shape) {
        this.shape = shape;
        return this;
    }

    public ClassroomBuilder setWidth(double width) {
        this.width = width;
        return this;
    }

    public ClassroomBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    public ClassroomBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    public Classroom build() {
        if (shape.equals("Circle")) {
            return new CircularClassroom(name, width,length, height);
        } else {
            return new RectangularClassroom(name, width, length, height);
        }
    }
}
