package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of Sprite objects that can be updated and drawn.
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<>();

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Calls timePassed() on all sprites in the collection.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites on the given surface.
     *
     * @param d the drawing surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
