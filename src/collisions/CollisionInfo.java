package collisions;

import geometry.Point;

/**
 * The CollisionInfo class represents information about a collision event
 * between a moving object (such as a ball) and a Collidable object.
 * It stores both the collision point and the object that was hit.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collidable;

    /**
     * Constructs a CollisionInfo object with the given collision point and collidable object.
     *
     * @param collisionPoint the exact point at which the collision occurred.
     * @param collidable the object that was collided with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collidable) {
        this.collisionPoint = collisionPoint;
        this.collidable = collidable;
    }

    /**
     * Returns the point at which the collision occurred.
     *
     * @return the collision point.
     */
    public Point getCollisionPoint() {
        return collisionPoint;
    }

    /**
     * Returns the object that was involved in the collision.
     *
     * @return the collidable object.
     */
    public Collidable getCollisionObject() {
        return collidable;
    }
}
