package listeners;

import utils.Counter;
import game.Game;
import sprites.Ball;
import sprites.Block;

/**
 * The BlockRemover class handles removing blocks from the game when hit by balls.
 * Implements HitListener to respond to collision events with color-matching logic.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructs a new BlockRemover.
     *
     * @param game the game instance from which blocks will be removed
     * @param remainingBlocks counter that tracks the number of blocks remaining
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Only remove block if colors don't match
        if (!beingHit.ballColorMatch(hitter)) {
            // Change ball color to match the block
            hitter.setColor(beingHit.getColor());

            // Remove this listener and the block from game
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(game);
            this.remainingBlocks.decrease(1);
        }
    }
}
