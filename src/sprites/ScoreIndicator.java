package sprites;

import biuoop.DrawSurface;
import utils.Counter;

import java.awt.Color;

/**
 * The ScoreIndicator class displays the current score on screen.
 * Implements Sprite to be drawn and updated with other game objects.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructs a new ScoreIndicator.
     *
     * @param score the counter that tracks the current score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        // Display score at top center of screen
        d.drawText(350, 18, "Score: " + score.getValue(), 16);
    }

    @Override
    public void timePassed() { }
}
