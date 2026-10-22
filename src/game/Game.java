package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import collisions.Collidable;
import geometry.Point;
import geometry.Rectangle;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Ball;
import sprites.Paddle;
import sprites.Block;
import sprites.ScoreIndicator;
import utils.Counter;

import java.awt.Color;

/**
 * The Game class initializes and runs the main game loop.
 * It manages sprites, collidables, and game state.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private KeyboardSensor keyboard;
    private Counter blockCounter;
    private Counter ballCounter;
    private Counter score;

    /**
     * Constructs a new Game with default screen size and input handling.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
        this.keyboard = gui.getKeyboardSensor();
        this.ballCounter = new Counter(0);
    }

    /**
     * Returns the sprite collection of the game.
     *
     * @return the sprite collection.
     */
    public SpriteCollection getSprites() {
        return sprites;
    }

    /**
     * Returns the game environment (collection of collidables).
     *
     * @return the game environment.
     */
    public GameEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Adds a collidable to the game environment.
     *
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the sprite collection.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the sprite collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Sets up the game: adds balls, paddle, boundary blocks, and a pyramid of colored blocks.
     * This method prepares all objects required for a playable level.
     */
    public void initialize() {
        // Create balls
        Ball ball1 = new Ball(new Point(400, 495), 4, Color.BLUE);
        Ball ball2 = new Ball(new Point(400, 500), 4, Color.BLUE);
        Ball ball3 = new Ball(new Point(400, 505), 4, Color.BLUE);

        for (Ball ball : new Ball[]{ball1, ball2, ball3}) {
            ball.setVelocity(2, -2);
            ball.setGame(this.environment);
            ball.addToGame(this);
            this.ballCounter.increase(1);
        }

        // Create paddle
        Rectangle paddleRect = new Rectangle(new Point(350, 560), 90, 7);
        Paddle paddle = new Paddle(keyboard, paddleRect, 5, 800);
        paddle.addToGame(this);

        // Screen boundaries (top, death region, left, right)
        Block top = new Block(new Rectangle(new Point(0, 20), 800, 20), Color.GRAY);
        Block deathRegion = new Block(new Rectangle(new Point(0, 610), 800, 20), Color.GRAY);
        Block left = new Block(new Rectangle(new Point(0, 20), 20, 580), Color.GRAY);
        Block right = new Block(new Rectangle(new Point(780, 20), 20, 580), Color.GRAY);

        for (Block b : new Block[]{top, deathRegion, left, right}) {
            b.addToGame(this);
        }

        // Add rows of blocks in a triangular layout
        Color[] rowColors = {Color.PINK, Color.CYAN, Color.RED, Color.GREEN, Color.MAGENTA, Color.ORANGE};
        int blockWidth = 50;
        int blockHeight = 20;
        int startY = 100;

        // Initialize counters and listeners
        this.blockCounter = new Counter(0);
        BlockRemover blockRemover = new BlockRemover(this, this.blockCounter);
        BallRemover ballRemover = new BallRemover(this, this.ballCounter);
        deathRegion.addHitListener(ballRemover);

        this.score = new Counter(0);
        ScoreTrackingListener scoreTrack = new ScoreTrackingListener(this.score);
        ScoreIndicator scoreDisplay = new ScoreIndicator(this.score);
        this.addSprite(scoreDisplay);

        // Create block pyramid (decreasing blocks per row)
        for (int row = 0; row < rowColors.length; row++) {
            Color rowColor = rowColors[row];
            int blocksInRow = 12 - row;
            int startX = 780 - blocksInRow * blockWidth;

            for (int i = 0; i < blocksInRow; i++) {
                int x = startX + i * blockWidth;
                int y = startY + row * blockHeight;
                Block block = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), rowColor);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTrack);
                block.addToGame(this);
                this.blockCounter.increase(1);
            }
        }
    }

    /**
     * Starts the animation loop. In each frame:
     * - all sprites are drawn
     * - the screen is updated
     * - each sprite is notified that time has passed
     * - the frame rate is regulated to run at 60 FPS
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        // Main game loop
        while (this.blockCounter.getValue() > 0 && this.ballCounter.getValue() > 0) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();

            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // Maintain frame rate
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

        // Handle game end
        if (this.blockCounter.getValue() == 0) {
            score.increase(100);
            System.out.println("You Win!\nYour score is: " + score.getValue());
        } else if (this.ballCounter.getValue() == 0) {
            System.out.println("Game Over.\nYour score is: " + score.getValue());
        }

        gui.close();
    }
}
