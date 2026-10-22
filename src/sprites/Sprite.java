package sprites;

import biuoop.DrawSurface;

/**
 * A Sprite is a game object that can be drawn and updated over time.
 */
public interface Sprite {

    /**
     * Draws the sprite on the given surface.
     *
     * @param d the drawing surface.
     */
    void drawOn(DrawSurface d);

    /**
     * Updates the sprite based on the passage of time.
     */
    void timePassed();
}
