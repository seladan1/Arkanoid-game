 /******
 Name: Dana Sela
 ID: 314813429
 Assignment: ass5
 *******/

 import game.Game;

 /**
 * Ass3Game is the entry point for the game.
 * It creates a Game instance, initializes the game objects, and starts the game loop.
 */
public class Ass5Game {
    /**
     * Launches the game.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
