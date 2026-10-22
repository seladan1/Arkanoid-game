package sprites;

import biuoop.DrawSurface;
import collisions.Collidable;
import game.Game;
import listeners.HitListener;
import listeners.HitNotifier;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangular block in the game that can be collided with.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Constructor to create a Block from a rectangle.
     *
     * @param rectangle the rectangle shape of the block.
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.color = Color.GRAY; // Default color
    }

    /**
     * Constructor to create a Block with specified rectangle and color.
     *
     * @param rectangle the rectangle shape of the block
     * @param color the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Returns the rectangle shape of this block.
     *
     * @return the block's rectangle
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * Returns the color of this block.
     *
     * @return the block's color
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Get block boundaries
        Point upperLeft = rectangle.getUpperLeft();
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();
        double leftX = upperLeft.getX();
        double rightX = upperLeft.getX() + width;
        double topY = upperLeft.getY();
        double bottomY = upperLeft.getY() + height;

        // Horizontal collision (left or right edge)
        if ((Math.abs(leftX - collisionPoint.getX()) < Point.THRESHOLD)
                || (Math.abs(rightX - collisionPoint.getX()) < Point.THRESHOLD)) {
            dx = -dx;
        }

        // Vertical collision (top or bottom edge)
        if ((Math.abs(topY - collisionPoint.getY()) < Point.THRESHOLD)
                || (Math.abs(bottomY - collisionPoint.getY()) < Point.THRESHOLD)) {
            dy = -dy;
        }

        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.getCollisionRectangle();
        Point upperLeft = r.getUpperLeft();
        // Fill the rectangle with block color
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) r.getWidth(), (int) r.getHeight());
        // Draw black border
        d.setColor(Color.BLACK);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) r.getWidth(), (int) r.getHeight());
    }

    @Override
    public void timePassed() {
        // Blocks are static and don't change with time
    }

    /**
     * Adds this block to the game as both a sprite and collidable.
     *
     * @param game the game to add this block to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * Checks if the ball's color matches this block's color.
     *
     * @param ball the ball to check
     * @return true if colors match, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        if (ball.getColor().equals(this.color)) {
            return true;
        }
        return false;
    }

    /**
     * Removes this block from the game.
     *
     * @param game the game to remove this block from
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all hit listeners that this block was hit.
     *
     * @param hitter the ball that hit this block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}