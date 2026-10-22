package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collisions.Collidable;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;

/**
 * Player-controlled paddle that can move horizontally and reflect balls.
 */
public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle rect;
    private int speed;
    private int screenWidth;

    /**
     * Creates a new paddle with keyboard control.
     *
     * @param keyboard the keyboard sensor for input
     * @param rect the rectangle representing the paddle
     * @param speed movement speed of the paddle
     * @param screenWidth width of the game screen
     */
    public Paddle(KeyboardSensor keyboard, Rectangle rect, int speed, int screenWidth) {
        this.keyboard = keyboard;
        this.rect = rect;
        this.speed = speed;
        this.screenWidth = screenWidth;
    }

    /**
     * Moves the paddle left, wrapping around if it reaches the edge.
     */
    public void moveLeft() {
        double newX = rect.getUpperLeft().getX() - speed;
        // Wrap around if paddle reaches left edge
        if (newX - 20 < 0) {
            newX = screenWidth - rect.getWidth() - 20;
        }
        Point newUpperLeft = new Point(newX, rect.getUpperLeft().getY());
        this.rect = new Rectangle(newUpperLeft, rect.getWidth(), rect.getHeight());
    }

    /**
     * Moves the paddle right, wrapping around if it reaches the edge.
     */
    public void moveRight() {
        double newX = rect.getUpperLeft().getX() + speed;
        // Wrap around if paddle reaches right edge
        if (newX + rect.getWidth() + 20 > screenWidth) {
            newX = 20;
        }
        Point newUpperLeft = new Point(newX, rect.getUpperLeft().getY());
        this.rect = new Rectangle(newUpperLeft, rect.getWidth(), rect.getHeight());
    }

    /**
     * Checks for keyboard input and moves paddle accordingly.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given surface.
     *
     * @param d the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        Point p = rect.getUpperLeft();
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle((int) p.getX(), (int) p.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        d.drawRectangle((int) p.getX(), (int) p.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    /**
     * Returns the shape of this paddle.
     *
     * @return the collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return rect;
    }

    /**
     * Handles collisions with the paddle, applying different reflection
     * angles based on which region of the paddle was hit.
     *
     * @param collisionPoint the point of collision
     * @param currentVelocity the velocity before collision
     * @return new velocity after collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Get paddle boundaries
        Point upperLeft = rect.getUpperLeft();
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double left = upperLeft.getX();
        double right = upperLeft.getX() + rect.getWidth();
        double top = upperLeft.getY();
        double bottom = upperLeft.getY() + rect.getHeight();

        // Top edge - apply special region-based reflection
        if (Math.abs(y - top) < Point.THRESHOLD) {
            return regionBound(x, dx, dy, left, rect.getWidth());
        }
        // Bottom edge - simple y reflection
        if (Math.abs(y - bottom) < Point.THRESHOLD) {
            return new Velocity(dx, -dy);
        }
        // Side edge - simple x reflection
        if (Math.abs(x - left) < Point.THRESHOLD || Math.abs(x - right) < Point.THRESHOLD) {
            return new Velocity(-dx, dy);
        }
        // Default case
        return new Velocity(dx, -dy);
    }

    /**
     * Calculates return angle based on which region of the paddle was hit.
     * The paddle is divided into 5 regions, each returning a different angle.
     *
     * @param x collision x-coordinate
     * @param dx current horizontal velocity
     * @param dy current vertical velocity
     * @param left left edge of paddle
     * @param width width of paddle
     * @return velocity with appropriate reflection angle
     */
    private Velocity regionBound(double x, double dx, double dy, double left, double width) {
        // Determine which of 5 regions was hit (1-5 from left to right)
        int region = (int) ((x - left) / (width / 5)) + 1;
        region = Math.max(1, Math.min(region, 5));
        double speed = Math.sqrt(dx * dx + dy * dy);

        // Return velocity based on region hit
        switch (region) {
            case 1:
                return Velocity.fromAngleAndSpeed(300, speed);  // Strong left
            case 2:
                return Velocity.fromAngleAndSpeed(330, speed);  // Slight left
            case 3:
                return new Velocity(dx, -dy);                   // Straight
            case 4:
                return Velocity.fromAngleAndSpeed(30, speed);   // Slight right
            case 5:
                return Velocity.fromAngleAndSpeed(60, speed);   // Strong right
            default:
                return new Velocity(dx, dy);
        }
    }

    /**
     * Adds this paddle to the game as both a sprite and collidable.
     *
     * @param g the game to add this paddle to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}