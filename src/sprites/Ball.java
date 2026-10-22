package sprites;

import biuoop.DrawSurface;
import collisions.Collidable;
import collisions.CollisionInfo;
import game.Game;
import game.GameEnvironment;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;

import java.awt.Color;

/**
 * Class of a ball, represented by a center point and a radius.
 */
public class Ball implements Sprite {
    // Basic properties of the ball
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    // Boundary elements for collision detection
    private Line[] frameBoundaries;
    private Point[] cornerPoints;
    private GameEnvironment game;

    /**
     * Creates a new ball with the specified center point, radius and color.
     *
     * @param center The center point of the ball
     * @param r The radius of the ball
     * @param color The color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Creates a new ball with the specified coordinates, radius and color.
     *
     * @param x The x-coordinate of the ball's center
     * @param y The y-coordinate of the ball's center
     * @param r The radius of the ball
     * @param color The color of the ball
     */
    public Ball(double x, double y, int r, Color color) {
        this(new Point(x, y), r, color);
    }

    /**
     * Gets the x value of the ball's center.
     *
     * @return The x value as an integer.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y value of the ball's center.
     *
     * @return The y value as an integer
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return The ball's radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The ball's color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the ball.
     *
     * @param newColor The new color
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * Gets the current velocity of the ball.
     *
     * @return The ball's velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Gets the game environment of the ball.
     *
     * @return The ball's game environment
     */
    public GameEnvironment getGame() {
        return this.game;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v The new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using horizontal and vertical components.
     *
     * @param dx The horizontal component of the velocity
     * @param dy The vertical component of the velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets the boundaries that the ball can bounce off.
     *
     * @param boundaries Array of lines representing the boundaries
     */
    public void setFrameBoundaries(Line[] boundaries) {
        this.frameBoundaries = boundaries;
    }

    /**
     * Sets the corner points that the ball can bounce off.
     *
     * @param corners Array of points representing corners
     */
    public void setCornerPoints(Point[] corners) {
        this.cornerPoints = corners;
    }

    /**
     * Sets the game environment for collision detection.
     *
     * @param game The game environment to set
     */
    public void setGame(GameEnvironment game) {
        this.game = game;
    }

    /**
     * Draws the ball on the given surface.
     *
     * @param surface The surface to draw on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * Advances the ball one time step.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Adds this ball to the specified game.
     *
     * @param game The game to add this ball to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    /**
     * Removes this ball from the specified game.
     *
     * @param g The game to remove this ball from
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

    /**
     * Moves the ball one step according to its velocity, handling collisions.
     */
    public void moveOneStep() {
        // Calculate trajectory line from current position to next position
        Line trajectory = new Line(center, velocity.applyToPoint(center));
        CollisionInfo colInfo = game.getClosestCollision(trajectory);

        // No collision detected, move to end position
        if (colInfo == null) {
            center = trajectory.end();
            return;
        }

        // Get collision details
        Point colPoint = colInfo.getCollisionPoint();
        Collidable colObject = colInfo.getCollisionObject();

        // Move ball to just before collision point
        double dx = velocity.getDx();
        double dy = velocity.getDy();
        double smallOffset = 1;
        double offsetX = dx == 0 ? 0 : -Math.signum(dx) * smallOffset;
        double offsetY = dy == 0 ? 0 : -Math.signum(dy) * smallOffset;
        center = new Point(colPoint.getX() + offsetX, colPoint.getY() + offsetY);

        // Update velocity based on collision
        velocity = colObject.hit(this, colPoint, velocity);
    }
}
