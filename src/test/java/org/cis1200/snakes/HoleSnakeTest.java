package org.cis1200.snakes;

import org.cis1200.snakes.gameObjects.Hole;
import org.cis1200.snakes.gameObjects.HoleSnake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class HoleSnakeTest {

    HoleSnake snake;

    @BeforeEach
    public void setup() {
        int[][] bodypos = { { 0, 0 } };
        snake = new HoleSnake(0, 0, 1, 0, bodypos);
    }

    @Test
    public void testIncrease() {
        for (int i = 1; i < HoleSnake.HOLES_RATIO; i++) {
            snake.increase();
            snake.move();
        }

        assertTrue(snake.getBody().getLast() instanceof Hole);
    }

    @Test
    public void testHeadCollision() {
        for (int i = 1; i < HoleSnake.HOLES_RATIO; i++) {
            snake.increase();
            snake.move();
        }
        snake.handleEvent(KeyEvent.VK_DOWN);
        snake.move();
        snake.handleEvent(KeyEvent.VK_LEFT);
        snake.move();
        snake.handleEvent(KeyEvent.VK_UP);
        snake.move();

        assertFalse(snake.collided());

    }

}
