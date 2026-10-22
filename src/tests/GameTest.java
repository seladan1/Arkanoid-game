package tests;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Block;

import java.awt.Color;

/**
 * Test class for a simple ball and block collision animation.
 */
public class GameTest {
    /**
     * Creates a game environment with walls, blocks and a bouncing ball.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Setup window and game.
        GUI gui = new GUI("Game Test", 800, 600);
        Sleeper sleeper = new Sleeper();
        GameEnvironment game = new GameEnvironment();

        // Create boundary walls.
        Block topWall = new Block(new Rectangle(new Point(0, 0), 800, 20));
        Block bottomWall = new Block(new Rectangle(new Point(0, 580), 800, 20));
        Block leftWall = new Block(new Rectangle(new Point(0, 0), 20, 600));
        Block rightWall = new Block(new Rectangle(new Point(780, 0), 20, 600));

        // Create interior blocks.
        Block center1 = new Block(new Rectangle(new Point(300, 200), 200, 30));
        Block center2 = new Block(new Rectangle(new Point(400, 400), 30, 100));

        // Add all blocks to game.
        Block[] allBlocks = {topWall, bottomWall, leftWall, rightWall, center1, center2};
        for (Block b : allBlocks) {
            game.addCollidable(b);
        }

        // Setup ball with velocity.
        Ball ball1 = new Ball(new Point(150, 150), 5, Color.RED);
        ball1.setVelocity(2, 0.68);
        ball1.setGame(game);

        // Animation loop.
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            // Draw blocks.
            for (Block b : allBlocks) {
                b.drawOn(d);
            }

            // Draw and move ball.
            ball1.drawOn(d);
            ball1.moveOneStep();
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}