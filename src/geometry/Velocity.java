package geometry;

/**
 * The Velocity class specifies the change in position on the `x` and the `y` axes.
 * It represents the speed and direction of movement.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a velocity with given x and y components.
     *
     * @param dx The change in x position per step
     * @param dy The change in y position per step
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a velocity from an angle and speed.
     * Angle is in degrees where 0 is up, 90 is right, 180 is down, 270 is left.
     *
     * @param angle The angle in degrees
     * @param speed The speed (magnitude of velocity)
     * @return A new Velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        // The negative sign converts from standard math orientation
        // (where 0° points right) to game orientation (where 0° points up)
        double dy = -speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }

    /**
     * Gets the horizontal component of the velocity.
     *
     * @return The change in x position per step
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets the vertical component of the velocity.
     *
     * @return The change in y position per step
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Applies velocity to a point, returning a new point after movement.
     *
     * @param p The original position point
     * @return A new point representing the position after applying velocity
     */
    public Point applyToPoint(Point p) {
        // Calculate new position by adding velocity components to current position
        double newX = this.dx + p.getX();
        double newY = this.dy + p.getY();
        return new Point(newX, newY);
    }
}