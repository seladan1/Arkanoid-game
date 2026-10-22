package geometry;

/**
 * Class representing a line using two points.
 */
public class Line {
    private Point start;
    private Point end;
    private boolean isLine;
    private double gradient;
    private double intercept;

    /**
     * Constructor using two Points.
     *
     * @param start Point 1.
     * @param end   Point 2.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        if (start.equals(end)) {
            isLine = false;
            gradient = 0;
            intercept = 0;
        } else {
            isLine = true;
            calcLineEquation();
        }
    }

    /**
     * Constructor using coordinates of 2 points.
     *
     * @param x1 double x value of first point.
     * @param y1 double y value of first point.
     * @param x2 double x value of second point.
     * @param y2 double y value of second point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        if (start.equals(end)) {
            isLine = false;
            gradient = 0;
            intercept = 0;
        } else {
            isLine = true;
            calcLineEquation();
        }
    }

    /**
     * Length of a line.
     *
     * @return double length.
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Calculates the middle point of a line.
     *
     * @return Point middle.
     */
    public Point middle() {
        double midX;
        double midY;
        midX = (start.getX() + end.getX()) / 2.0;
        midY = (start.getY() + end.getY()) / 2.0;
        return new Point(midX, midY);
    }

    /**
     * Returns the start point of the line.
     *
     * @return Point start.
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return Point end.
     */
    public Point end() {
        return end;
    }

    /**
     * Returns whether the line is a line.
     *
     * @return boolean isLine.
     */
    public boolean getIsLine() {
        return isLine;
    }

    /**
     * Returns the gradient of the line.
     *
     * @return double gradient.
     */
    public double getGradient() {
        return gradient;
    }

    /**
     * Returns the intercept of the line.
     *
     * @return double intercept.
     */
    public double getIntercept() {
        return intercept;
    }

    /**
     * Calculates the gradient and intercept of the line.
     */
    public void calcLineEquation() {
        double diffX;
        double diffY;

        // Calculate the gradient of the line.
        diffX = end.getX() - start.getX();
        diffY = end.getY() - start.getY();
        if (Math.abs(diffX) <= Point.THRESHOLD) {
            gradient = Double.POSITIVE_INFINITY;
            intercept = start.getX();
        } else {
            gradient = diffY / diffX;
            // Calculate the interception.
            intercept = start.getY() - (start.getX() * gradient);
        }
    }

    /**
     * Checks if a point is on the line.
     *
     * @param p The point to check.
     * @return int 0 if the point is not on the line, 1 if the point is an edge, 2 if the point is on the line.
     */
    public int isPointOnLine(Point p) {
        int strangerLines = 0;
        int edgeOfLine = 1;
        int multipleIntercepts = 2;

        // Check if point is at either endpoint.
        if (p.equals(start) || p.equals(end)) {
            return edgeOfLine;
        }

        // Check if point is within the line's range.
        double minX = Math.min(start.getX(), end.getX());
        double maxX = Math.max(start.getX(), end.getX());
        double minY = Math.min(start.getY(), end.getY());
        double maxY = Math.max(start.getY(), end.getY());

        if (p.getX() < minX - Point.THRESHOLD || p.getX() > maxX + Point.THRESHOLD
                || p.getY() < minY - Point.THRESHOLD || p.getY() > maxY + Point.THRESHOLD) {
            return strangerLines; // Point is outside the line's range.
        }

        // Special case for vertical lines
        if (Math.abs(start.getX() - end.getX()) < Point.THRESHOLD) {
            if (Math.abs(p.getX() - start.getX()) < Point.THRESHOLD) {
                return multipleIntercepts; // Point is on the vertical line
            }
            return strangerLines;
        }

        // Special case for horizontal lines
        if (Math.abs(start.getY() - end.getY()) < Point.THRESHOLD) {
            if (Math.abs(p.getY() - start.getY()) < Point.THRESHOLD) {
                return multipleIntercepts; // Point is on the horizontal line
            }
            return strangerLines;
        }

        // For non-vertical, non-horizontal lines
        // Check if point is on the line equation.
        if (Math.abs(p.getY() - (gradient * p.getX() + intercept)) < Point.THRESHOLD) {
            return multipleIntercepts;
        }

        return strangerLines;
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other The other line to check intersection with.
     * @return boolean true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // If both are points, check if they're the same point
        if (!isLine && !other.getIsLine()) {
            return start.equals(other.start);
        }

        // If one is a point, check if it lies on the other line
        if (!isLine && other.getIsLine()) {
            return other.isPointOnLine(start) > 0;
        }
        if (isLine && !other.getIsLine()) {
            return isPointOnLine(other.start) > 0;
        }

        // Check if the lines share an endpoint
        if (start.equals(other.start) || start.equals(other.end)
                || end.equals(other.start) || end.equals(other.end)) {
            return true;
        }

        // Handle horizontal and vertical lines explicitly
        boolean thisHorizontal = Math.abs(start.getY() - end.getY()) < Point.THRESHOLD;
        boolean otherHorizontal = Math.abs(other.start.getY() - other.end.getY()) < Point.THRESHOLD;
        boolean thisVertical = Math.abs(start.getX() - end.getX()) < Point.THRESHOLD;
        boolean otherVertical = Math.abs(other.start.getX() - other.end.getX()) < Point.THRESHOLD;

        // Calculate intersection point
        Point intersection = null;

        // Both lines are horizontal
        if (thisHorizontal && otherHorizontal) {
            // If at same Y value, check for X-overlap
            if (Math.abs(start.getY() - other.start.getY()) < Point.THRESHOLD) {
                double minX1 = Math.min(start.getX(), end.getX());
                double maxX1 = Math.max(start.getX(), end.getX());
                double minX2 = Math.min(other.start.getX(), other.end.getX());
                double maxX2 = Math.max(other.start.getX(), other.end.getX());
                return !(maxX1 < minX2 || maxX2 < minX1);
            }
            return false;
        }

        // Both lines are vertical
        if (thisVertical && otherVertical) {
            // If at same X value, check for Y-overlap
            if (Math.abs(start.getX() - other.start.getX()) < Point.THRESHOLD) {
                double minY1 = Math.min(start.getY(), end.getY());
                double maxY1 = Math.max(start.getY(), end.getY());
                double minY2 = Math.min(other.start.getY(), other.end.getY());
                double maxY2 = Math.max(other.start.getY(), other.end.getY());
                return !(maxY1 < minY2 || maxY2 < minY1);
            }
            return false;
        }

        // Horizontal line and vertical line
        if (thisHorizontal && otherVertical) {
            double y = start.getY();
            double x = other.start.getX();
            intersection = new Point(x, y);
        } else if (thisVertical && otherHorizontal) {
            double x = start.getX();
            double y = other.start.getY();
            intersection = new Point(x, y);
        } else {
            // General case: non-parallel, non-vertical/horizontal lines

            // Check if the lines are parallel
            if (Math.abs(gradient - other.gradient) < Point.THRESHOLD) {
                // If they have the same intercept, check for overlap
                if (Math.abs(intercept - other.intercept) < Point.THRESHOLD) {
                    // Check for overlap
                    double minX1 = Math.min(start.getX(), end.getX());
                    double maxX1 = Math.max(start.getX(), end.getX());
                    double minX2 = Math.min(other.start.getX(), other.end.getX());
                    double maxX2 = Math.max(other.start.getX(), other.end.getX());
                    return !(maxX1 < minX2 || maxX2 < minX1);
                }
                return false;
            }

            // One line is vertical
            if (thisVertical) {
                double x = start.getX();
                double y = other.gradient * x + other.intercept;
                intersection = new Point(x, y);
            } else if (otherVertical) {
                double x = other.start.getX();
                double y = gradient * x + intercept;
                intersection = new Point(x, y);
            } else {
                // Calculate intersection for two non-vertical lines
                double x = (other.intercept - intercept) / (gradient - other.gradient);
                double y = gradient * x + intercept;
                intersection = new Point(x, y);
            }
        }

        // Check if the intersection point is on both line segments
        return isPointInRange(intersection) && other.isPointInRange(intersection);
    }

    /**
     * Helper method to check if a point is on a line segment.
     *
     * @param p The point to check.
     * @return boolean true if the point is within the line segment's bounds, false otherwise.
     */
    public boolean isPointInRange(Point p) {
        if (p == null) {
            return false;
        }

        double minX = Math.min(start.getX(), end.getX());
        double maxX = Math.max(start.getX(), end.getX());
        double minY = Math.min(start.getY(), end.getY());
        double maxY = Math.max(start.getY(), end.getY());

        // Check if point is within bounding box (with threshold)
        return (p.getX() >= minX - Point.THRESHOLD
                && p.getX() <= maxX + Point.THRESHOLD
                && p.getY() >= minY - Point.THRESHOLD
                && p.getY() <= maxY + Point.THRESHOLD);
    }

    /**
     * Checks if this line intersects with two other lines.
     *
     * @param other1 The first other line.
     * @param other2 The second other line.
     * @return boolean true if this line intersects with both other lines, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return (isIntersecting(other1) && isIntersecting(other2));
    }

    /**
     * Returns the intersection point of this line with another line.
     *
     * @param other The other line.
     * @return The intersection point, or null if the lines do not intersect.
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            return null;
        }

        // Handle the case where one "line" is actually a point
        if (!isLine && other.getIsLine()) {
            return new Point(start.getX(), start.getY());
        }
        if (isLine && !other.getIsLine()) {
            return new Point(other.start.getX(), other.start.getY());
        }

        // If both lines are vertical
        if (this.gradient == Double.POSITIVE_INFINITY && other.gradient == Double.POSITIVE_INFINITY) {
            // Check if they are on the same vertical line
            if (this.start.getX() == other.start.getX()) {
                // Check if they connect or overlap
                double minY1 = Math.min(this.start.getY(), this.end.getY());
                double maxY1 = Math.max(this.start.getY(), this.end.getY());
                double minY2 = Math.min(other.start.getY(), other.end.getY());
                double maxY2 = Math.max(other.start.getY(), other.end.getY());

                if (maxY1 < minY2 || maxY2 < minY1) {
                    return null; // Lines don't intersect at all
                }
                if (maxY1 == minY2) {
                    return new Point(this.start.getX(), maxY1); // They intersect at an edge
                }
                if (maxY2 == minY1) {
                    return new Point(this.start.getX(), maxY2); // They intersect at the other edge
                }
            }
            return null; // Separate vertical lines
        }

        if (this.gradient == Double.POSITIVE_INFINITY) {
            double x = this.start.getX();
            double y = other.gradient * x + other.intercept;
            Point intersection = new Point(x, y);
            // Check if the point is within range of both lines
            if (isPointInRange(intersection) && other.isPointInRange(intersection)) {
                return intersection;
            }
            return null;
        }
        if (other.gradient == Double.POSITIVE_INFINITY) {
            double x = other.start.getX();
            double y = this.gradient * x + this.intercept;
            Point intersection = new Point(x, y);
            // Check if the point is within range of both lines
            if (isPointInRange(intersection) && this.isPointInRange(intersection)) {
                return intersection;
            }
            return null;
        }

        //Handle cases of one or more lines thar are points.
        if (!isLine && !other.getIsLine()) {
            return start;
        }
        if (!isLine && other.getIsLine()) {
            if (isPointOnLine(start) != 0) {
                return start;
            }
        }
        if (isLine && !other.getIsLine()) {
            if (isPointOnLine(other.start) != 0) {
                return other.start;
            }
        }

        double finalGradient = gradient - other.getGradient();
        double finalIntercept = other.getIntercept() - intercept;

        if (finalGradient == 0) {
            // If gradients are equal, lines are parallel or coincident
            if (isPointOnLine(other.start) == 2 || isPointOnLine(other.end) == 2) {
                return null;
            }

            if (isPointOnLine(other.start) == 1 && isPointOnLine(other.end) == 0) {
                // If 'other' start is an edge of 'this' and 'other' end is not on 'this'
                if (start.getX() > Math.min(other.start.getX(), other.end.getX())
                        && start.getX() < Math.max(other.start.getX(), other.end.getX())) {
                    return null;
                }
                if (end.getX() > Math.min(other.start.getX(), other.end.getX())
                        && end.getX() < Math.max(other.start.getX(), other.end.getX())) {
                    return null;
                }
                // Return 'other' start as the intersection point
                return other.start;
            }

            if (isPointOnLine(other.start) == 0 && isPointOnLine(other.end) == 1) {
                // If 'other' end is an edge of 'this' and 'other' start is not on 'this'
                if (start.getX() > Math.min(other.start.getX(), other.end.getX())
                        && start.getX() < Math.max(other.start.getX(), other.end.getX())) {
                    return null;
                }
                if (end.getX() > Math.min(other.start.getX(), other.end.getX())
                        && end.getX() < Math.max(other.start.getX(), other.end.getX())) {
                    return null;
                }
                // Return 'other' end as the intersection point
                return other.end;
            }

            if (Math.max(other.start.getX(), other.end.getX()) > Math.max(start.getX(), end.getX())
                    && Math.min(other.start.getX(), other.end.getX()) < Math.min(start.getX(), end.getX())) {
                // If 'other' x-range fully contains 'this' x-range, no intersection
                return null;
            }
        }
        double intersectionX = finalIntercept / finalGradient;
        double intersectionY = (gradient * intersectionX) + intercept;
        return new Point(intersectionX, intersectionY);
    }

    /**
     * Checks if this line is equal to another line.
     *
     * @param other The other line.
     * @return boolean true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if (start.equals(other.start) && end.equals(other.end)) {
            return true;
        }
        if (start.equals(other.end) && end.equals(other.start)) {
            return true;
        }
        return false;
    }

    /**
     * Finds the closest intersection point of this line with a rectangle.
     *
     * @param rect the rectangle to check intersection with
     * @return the closest intersection point to the start of the line, or null if no intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Get all intersection points between the line and rectangle
        java.util.List<Point> intersections = rect.intersectionPoints(this);

        // If no intersections exist, return null
        if (intersections.isEmpty()) {
            return null;
        }

        // Start with the first intersection point
        Point closestIntersection = intersections.get(0);
        double minDist = start.distance(closestIntersection);

        // Find the intersection point with minimum distance from start
        for (Point intersection : intersections) {
            double dist = start.distance(intersection);
            if (dist < minDist) {
                closestIntersection = intersection;
                minDist = dist;
            }
        }

        return closestIntersection;
    }
}