package tests;

import listeners.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * A test implementation of HitListener that prints messages when blocks are hit.
 * Used for debugging and testing collision detection.
 */
public class PrintingHitListener implements HitListener {

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}
