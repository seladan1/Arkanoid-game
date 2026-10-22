package listeners;

/**
 * Interface for objects that can notify listeners about hit events.
 * Provides methods to add and remove hit event listeners.
 */
public interface HitNotifier {

    /**
     * Adds a hit listener to be notified of hit events.
     *
     * @param hl the hit listener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a hit listener from the list of listeners.
     *
     * @param hl the hit listener to remove
     */
    void removeHitListener(HitListener hl);
}
