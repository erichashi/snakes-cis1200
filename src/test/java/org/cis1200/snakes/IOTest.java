package org.cis1200.snakes;

import org.cis1200.snakes.gameObjects.GameStateConditions;
import org.cis1200.snakes.gameObjects.Hole;
import org.cis1200.snakes.panels.GamePanel;
import org.cis1200.snakes.panels.Main;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class IOTest {

    Main game;

    @Test
    public void testIOInput() {
        GameStateConditions gsc = Helpers.getCSVData(RunSnakes.PATH_TO_INIT_DATA, 0);

        game = new Main(
                new GamePanel(
                        new CardLayout(),
                        new JPanel()
                ), gsc
        );

        assertEquals(5, game.getSnake().getHead().getRow());
        assertEquals(5, game.getSnake().getHead().getCol());

        assertEquals(1, game.getSnake().getHdir());
        assertEquals(0, game.getSnake().getVdir());

        assertEquals(
                4,
                game.getSnake().getBody().getLast().getCol()
        );
        assertEquals(
                5,
                game.getSnake().getBody().getLast().getRow()
        );

        assertFalse(game.getSnake().getBody().getLast() instanceof Hole);

        assertEquals(0, game.getSnake().getType());

    }

    @Test
    public void testIOOutput() {
        GameStateConditions gsc = Helpers.getCSVData(RunSnakes.PATH_TO_INIT_DATA, 0);

        game = new Main(
                new GamePanel(
                        new CardLayout(),
                        new JPanel()
                ), gsc
        );

        game.getSnake().move();
        game.getSnake().increase();
        game.getSnake().move();
        game.getSnake().increase();
        game.getSnake().move();
        game.getSnake().increase();
        game.getSnake().move();
        game.getSnake().increase();
        game.getSnake().move();
        game.getSnake().move();

        String expected = "5-11,1,0,0-5-11e0-5-10e0-5-9e0-5-8e0-5-7e0-5-6,5-10,0,no";

        assertEquals(
                expected,
                Helpers.stateToString(
                        game.getSnake(),
                        game.getFruit()
                )
        );

    }

}
