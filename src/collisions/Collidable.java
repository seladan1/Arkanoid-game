package collisions;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Ball;

/**
 * Interface for objects that can be collided with in the game.
 * Provides methods for collision detection and response.
 */
public interface Collidable {
    /**
     * Returns the shape of this collidable object for collision detection.
     *
     * @return the collision rectangle representing this object's bounds
     */
    Rectangle getCollisionRectangle();

    /**
     * Calculates the new velocity after a collision with this object.
     * Called when a collision is detected to determine how the colliding object
     * should respond.
     *
     * @param hitter the Ball that hits the relevant Block
     * @param collisionPoint the point at which the collision occurred
     * @param currentVelocity the velocity of the colliding object before collision
     * @return the new velocity expected after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}