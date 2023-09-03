package org.cis1200.snakes.panels;

import org.cis1200.snakes.*;
import org.cis1200.snakes.gameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */

public class Main extends JPanel {

    // Game constants
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 20;
    public static final int COURT_WIDTH = BOARD_WIDTH * BLOCK_SIZE;
    public static final int COURT_HEIGHT = BOARD_HEIGHT * BLOCK_SIZE;
    public static final int INTERVAL = 200;
    public static final int STANDARD_SNAKE = 0;
    public static final int PORTAL_SNAKE = 1;
    public static final int HOLE_SNAKE = 2;

    // the state of the game logics
    private Snake snake;
    private Board board;
    private Fruit fruit;
    private GameStateConditions data;
    private boolean playing = false; // whether the game is running
    private boolean gameover = false; // whether the game is running

    private final GamePanel gamePanel;

    public Snake getSnake() {
        return snake;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setSnake(int snakeType) {
        if (snakeType == STANDARD_SNAKE) {
            snake = new Snake(
                    this.data.getHpos()[0], this.data.getHpos()[1], this.data.getHdir(),
                    this.data.getVdir(), this.data.getBodypos()
            );

        } else if (snakeType == PORTAL_SNAKE) {
            snake = new PortalSnake(
                    this.data.getHpos()[0], this.data.getHpos()[1], this.data.getHdir(),
                    this.data.getVdir(), this.data.getBodypos(), this.data.getPortalpos()
            );

        } else if (snakeType == HOLE_SNAKE) {
            snake = new HoleSnake(
                    this.data.getHpos()[0], this.data.getHpos()[1], this.data.getHdir(),
                    this.data.getVdir(), this.data.getBodypos()
            );
        }
    }

    public void setData(GameStateConditions data) {
        this.data = data;
    }

    public Main(GamePanel gamePanel, GameStateConditions data) {

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start(); // MAKE SURE TO START THE TIMER!

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                snake.handleEvent(e.getKeyCode());
            }

            // public void keyReleased(KeyEvent e) {
            // square.setVx(0);
            // square.setVy(0);
            // }
        });

        this.data = data;
        this.gamePanel = gamePanel;

        reset(data);

        this.gamePanel.scoreLabel.setText(String.valueOf(this.snake.getLen()));

        requestFocusInWindow();
    }

    public void reset(GameStateConditions gsc) {
        setData(gsc);

        setSnake(gsc.getType()); // set snake to 0 for now
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        fruit = new Fruit(this.data.getFpos()[0], this.data.getFpos()[1]);

        playing = false;
        gameover = false;

        repaint();

        requestFocusInWindow();
    }

    public void pause() {
        playing = false;
        repaint();

        requestFocusInWindow();
    }

    public void play() {
        playing = true;
        requestFocusInWindow();
    }

    public void saveAndQuit() {
        // data = "hs, snake head pos, snake hdir, vdir, body as string of array, fruit
        // pos"
        // 1,5-5,1,0,5-5e5-4,5-10
        Helpers.writeStringToFile(
                Helpers.stateToString(this.snake, this.fruit),
                RunSnakes.PATH_TO_SAVED_DATA, false
        );

    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing && !gameover) {
            snake.update();

            // Snake collision with body
            if (this.snake.collided()) {
                this.gameover = true;

                if (this.snake.getLen() > this.data.getHs()) {
                    Helpers.writeStringToFile(
                            String.valueOf(this.snake.getLen()), RunSnakes.PATH_TO_HIGUEST_SCORE,
                            false
                    );
                }

                // delete data from saved
                Helpers.writeStringToFile("", RunSnakes.PATH_TO_SAVED_DATA, false);

                this.gamePanel.gameover();
            }

            // Fruit collision
            if (this.snake.getHead().getCol() == this.fruit.getCol() &&
                    this.snake.getHead().getRow() == this.fruit.getRow()) {

                this.snake.increase();
                this.gamePanel.scoreLabel.setText(String.valueOf(this.snake.getLen()));
                this.fruit = new Fruit(
                        Helpers.getRandomInteger(0, BOARD_HEIGHT - 1),
                        Helpers.getRandomInteger(0, BOARD_WIDTH - 1)
                );

            }

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        board.draw(g);
        fruit.draw(g);
        snake.draw(g);

        if (!playing) {
            g.setColor(new Color(10, 10, 10, 80));
            g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);

            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Sans Serif", Font.PLAIN, 40));
            g.drawString("PAUSE", COURT_WIDTH / 2 - 50, COURT_HEIGHT / 2 - 20);

        } else if (gameover) {
            g.setColor(new Color(10, 10, 10, 80));
            g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);

            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Sans Serif", Font.PLAIN, 30));
            g.drawString("GAME OVER", COURT_WIDTH / 2 - 50, COURT_HEIGHT / 2 - 20);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}