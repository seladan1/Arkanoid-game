package utils;

/**
 * The Counter class provides a simple integer counter with increase/decrease operations.
 * Used for tracking game statistics like score, remaining blocks, and ball count.
 */
public class Counter {
    private int value;

    /**
     * Constructs a new Counter with the specified starting value.
     *
     * @param startVal the initial value of the counter
     */
    public Counter(int startVal) {
        this.value = startVal;
    }

    /**
     * Adds the specified number to the current count.
     *
     * @param number the amount to add to the counter
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * Subtracts the specified number from the current count.
     *
     * @param number the amount to subtract from the counter
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * Returns the current count value.
     *
     * @return the current value of the counter
     */
    public int getValue() {
        return this.value;
    }
}
