package org.cis1200.snakes;

import org.cis1200.snakes.gameObjects.Snake;
import org.cis1200.snakes.panels.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    Snake snake;

    @BeforeEach
    public void setup() {
        int[][] bodypos = { { 0, 0 } };
        snake = new Snake(0, 0, 1, 0, bodypos);
    }

    @Test
    public void testLength() {
        snake.increase();
        assertEquals(2, snake.getLen());
    }

    @Test
    public void testLength2() {
        snake.increase();
        snake.increase();
        snake.increase();
        assertEquals(4, snake.getLen());
    }

    @Test
    public void testDirection() {
        snake.handleEvent(KeyEvent.VK_UP);
        assertEquals(-1, snake.getVdir());
        assertEquals(0, snake.getHdir());
    }

    @Test
    public void testDirectionCannotTurnAround() {
        snake.handleEvent(KeyEvent.VK_RIGHT);
        assertEquals(0, snake.getVdir());
        assertEquals(1, snake.getHdir());
    }

    @Test
    public void testMove() {
        snake.increase();
        snake.move();
        snake.move();
        assertEquals(0, snake.getHead().getRow());
        assertEquals(2, snake.getHead().getCol());
    }

    @Test
    public void testMoveWithChangeDirection() {
        snake.move();
        snake.move();
        assertEquals(0, snake.getHead().getRow());
        assertEquals(2, snake.getHead().getCol());
        snake.handleEvent(KeyEvent.VK_DOWN);
        assertEquals(1, snake.getVdir());
        assertEquals(0, snake.getHdir());
        snake.move();
        snake.move();
        snake.move();
        assertEquals(3, snake.getHead().getRow());
        assertEquals(2, snake.getHead().getCol());
    }

    @Test
    public void testMoveBorders() {
        // right -> left
        int[][] bodypos = { { 0, Main.BOARD_WIDTH - 1 } };
        snake = new Snake(0, Main.BOARD_WIDTH - 1, 1, 0, bodypos);

        snake.move();
        assertEquals(0, snake.getHead().getRow());
        assertEquals(0, snake.getHead().getCol());

        // left -> right
        bodypos = new int[][] { { 0, 0 } };
        snake = new Snake(0, 0, -1, 0, bodypos);

        snake.move();
        assertEquals(0, snake.getHead().getRow());
        assertEquals(Main.BOARD_WIDTH - 1, snake.getHead().getCol());

        // up -> bottom
        bodypos = new int[][] { { 0, 0 } };
        snake = new Snake(0, 0, 0, -1, bodypos);

        snake.move();
        assertEquals(Main.BOARD_HEIGHT - 1, snake.getHead().getRow());
        assertEquals(0, snake.getHead().getCol());

        // bottom -> up
        bodypos = new int[][] { { Main.BOARD_HEIGHT - 1, 0 } };
        snake = new Snake(Main.BOARD_HEIGHT - 1, 0, 0, 1, bodypos);

        snake.move();
        assertEquals(0, snake.getHead().getRow());
        assertEquals(0, snake.getHead().getCol());
    }

    @Test
    public void testBodyPosition() {
        snake.increase();
        snake.move();
        snake.increase();
        snake.move();
        snake.increase();
        snake.move();
        snake.move();

        RunSnakes.SquareUnit[] bodyArr = new RunSnakes.SquareUnit[snake.getLen()];
        snake.getBody().toArray(bodyArr);

        for (int i = 0; i < snake.getLen(); i++) {
            assertEquals(snake.getLen() - i, bodyArr[i].getCol());
            assertEquals(0, bodyArr[i].getRow());
        }

    }

    @Test
    public void testBodyCollision() {
        snake.increase();
        snake.increase();
        snake.increase();
        snake.increase();
        snake.move();
        snake.move();
        snake.move();
        snake.move();
        snake.move();

        snake.handleEvent(KeyEvent.VK_DOWN);
        snake.move();

        snake.handleEvent(KeyEvent.VK_LEFT);
        snake.move();

        snake.handleEvent(KeyEvent.VK_UP);
        snake.move();

        assertTrue(snake.collided());
    }

}
