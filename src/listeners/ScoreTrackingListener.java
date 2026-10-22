package listeners;

import utils.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * The ScoreTrackingListener class handles scoring when blocks are hit.
 * Implements HitListener to respond to collision events.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructs a new ScoreTrackingListener.
     *
     * @param scoreCounter the counter that tracks the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Award 5 points for hitting a block
        this.currentScore.increase(5);
    }
}
