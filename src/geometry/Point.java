package geometry;

/**
 * Class of a point, represented by x and y.
 */
public class Point {
    private double x;
    private double y;
    public static final double THRESHOLD = 0.00001;

    /**
     * Constructor using two values.
     *
     * @param x x value
     * @param y y value
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Distance between two points.
     *
     * @param other Point to calculate the distance to.
     * @return double distance of this point to the other point.
     */
    public double distance(Point other) {
        double lengthX = this.x - other.x;
        double lengthY = this.y - other.y;
        return Math.sqrt(Math.pow(lengthX, 2) + Math.pow(lengthY, 2));
    }

    /**
     * Compare two points by threshold.
     *
     * @param other Point to compare.
     * @return boolean true is the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        double compareX = Math.abs(this.x - other.x);
        double compareY = Math.abs(this.y - other.y);
        return compareX <= THRESHOLD && compareY <= THRESHOLD;
    }

    /**
     * Return the x value of this point.
     *
     * @return double x value.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of this point.
     *
     * @return double y value.
     */
    public double getY() {
        return this.y;
    }
}