package listeners;

import utils.Counter;
import game.Game;
import sprites.Ball;
import sprites.Block;

/**
 * The BallRemover class is responsible for removing balls from the game
 * when they hit certain blocks (typically the bottom boundary).
 * It implements the HitListener interface to respond to collision events.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructs a new BallRemover.
     *
     * @param game the game instance from which balls will be removed
     * @param remainingBalls counter that tracks the number of balls remaining in the game
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        this.remainingBalls.decrease(1);
    }
}
