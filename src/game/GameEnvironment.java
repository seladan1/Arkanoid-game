package game;

import collisions.Collidable;
import collisions.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all collidable objects in the game and handles collision detection.
 * Acts as a container and collision detector for game objects.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Creates a new game environment with an empty collection of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds the given collidable object to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Removes the given collidable object from the environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Returns the list of collidable objects in the environment.
     *
     * @return list of all collidable objects
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Finds the closest collision that would occur along a trajectory line.
     * Checks all collidables and returns information about the closest collision.
     *
     * @param trajectory the line representing the object's movement path
     * @return collision information of the closest collision, or null if no collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestCollidable = null;
        double closestDistance = Double.MAX_VALUE;

        // Check each collidable for potential intersections
        for (Collidable collidable : collidables) {
            Rectangle rect = collidable.getCollisionRectangle();
            List<Point> collisions = rect.intersectionPoints(trajectory);

            // Skip if no collision with this object
            if (collisions.isEmpty()) {
                continue;
            }

            // Find the closest collision point
            for (Point p : collisions) {
                double distance = p.distance(trajectory.start());
                if (distance < closestDistance) {
                    closestPoint = p;
                    closestDistance = distance;
                    closestCollidable = collidable;
                }
            }
        }

        // Return null if no collision found
        if (closestPoint == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestCollidable);
    }
}