package org.cis1200.snakes;

import org.cis1200.snakes.gameObjects.PortalSnake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PortalSnakeTest {
    PortalSnake snake;

    @BeforeEach
    public void setup() {
        int[][] bodypos = { { 0, 0 } };
        snake = new PortalSnake(0, 0, 1, 0, bodypos, new int[] { 1, 1 });

    }

    @Test
    public void portalActiveTest() {
        snake.move();
        snake.createPortal();
        assertTrue(snake.isPortalActive());

        // int[][] bodypos = {{0,0}};
        // Snake s = new PortalSnake(0, 0, 1, 0, bodypos);
        // System.out.println(s instanceof PortalSnake);
    }

    @Test
    public void findTailTest() {
        snake.increase();
        snake.increase();
        snake.move();
        snake.move();
        snake.findTail();
        assertEquals(0, snake.getTail().getRow());
        assertEquals(0, snake.getTail().getCol());
    }

    @Test
    public void teleportTest() {
        snake.increase();
        snake.increase();
        snake.move();
        snake.move();

        snake.createPortal();
        assertTrue(snake.isPortalActive());
        assertEquals(0, snake.getPortal().getRow());
        assertEquals(0, snake.getPortal().getCol());

        snake.move();
        snake.move();
        snake.move();

        assertEquals(5, snake.getHead().getCol());
        assertEquals(0, snake.getHead().getRow());

        snake.teleport();

        assertEquals(0, snake.getHead().getCol());
        assertEquals(0, snake.getHead().getRow());
    }

}
