package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangle with position and dimensions.
 * Contains methods for intersection detection with lines.
 */
public class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;
    private Line top;
    private Line bottom;
    private Line left;
    private Line right;

    /**
     * Creates a new rectangle with location and dimensions.
     *
     * @param upperLeft the upper-left corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;

        // Calculate the other corner points
        Point upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point bottomRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);

        // Create the four borderlines
        top = new Line(upperLeft, upperRight);
        bottom = new Line(bottomLeft, bottomRight);
        left = new Line(upperLeft, bottomLeft);
        right = new Line(upperRight, bottomRight);
    }

    /**
     * Finds all intersection points between this rectangle and a line.
     *
     * @param line the line to check for intersections
     * @return list of intersection points (might be empty)
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();
        Line[] sides = {top, bottom, left, right};
        // Check each side of the rectangle for intersection
        for (Line side : sides) {
            Point p = side.intersectionWith(line);
            if (p != null) {
                intersections.add(p);
            }
        }
        return intersections;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the rectangle's width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the rectangle's height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return the upper-left corner point
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Returns the top borderline of the rectangle.
     *
     * @return the top borderline
     */
    public Line getTopBorder() {
        return top;
    }

    /**
     * Returns the bottom borderline of the rectangle.
     *
     * @return the bottom borderline
     */
    public Line getBottomBorder() {
        return bottom;
    }

    /**
     * Returns the left borderline of the rectangle.
     *
     * @return the left borderline
     */
    public Line getLeftBorder() {
        return left;
    }

    /**
     * Returns the right borderline of the rectangle.
     *
     * @return the right borderline
     */
    public Line getRightBorder() {
        return right;
    }
}