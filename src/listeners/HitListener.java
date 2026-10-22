package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * Interface for objects that want to be notified when hit events occur.
 * Listeners implement this interface to respond to collision events.
 */
public interface HitListener {

    /**
     * Called whenever a block is hit by a ball.
     *
     * @param beingHit the block that was hit
     * @param hitter the ball that performed the hit
     */
    void hitEvent(Block beingHit, Ball hitter);
}
