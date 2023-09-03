package org.cis1200.snakes.gameObjects;

import org.cis1200.snakes.RunSnakes;
import org.cis1200.snakes.panels.Main;

import java.awt.*;

public class HoleSnake extends Snake {

    public static final int HOLES_RATIO = 3;
    public static final Color HOLE_SNAKE_COLOR = Color.magenta;
    public static final Color HOLE_COLOR = Color.yellow;

    public HoleSnake(int row, int col, int hdir, int vdir, int[][] bodypos) {
        super(row, col, hdir, vdir, bodypos);
        this.setColor(HOLE_SNAKE_COLOR);
        this.setType(Main.HOLE_SNAKE);

    }

    public void increase() {
        this.setLen(this.getLen() + 1);

        RunSnakes.SquareUnit s;
        if (this.getLen() % HOLES_RATIO == 0) {
            s = new Hole(this.getHead().getRow(), this.getHead().getCol());
        } else {
            s = new RunSnakes.SquareUnit(this.getHead().getRow(), this.getHead().getCol());
        }
        this.getBody().add(s);

    }

    public boolean checkHeadCollision(RunSnakes.SquareUnit b, int[] futureHeadPos) {
        return (b instanceof Hole) && b.getRow() == futureHeadPos[0]
                && b.getCol() == futureHeadPos[1];
    }

    public void drawBody(Graphics g) {
        for (RunSnakes.SquareUnit s : this.getBody()) {

            if (s instanceof Hole) {
                g.setColor(HOLE_COLOR);
            } else {
                g.setColor(this.getColor());
            }
            g.fillRect(
                    s.getCol() * Main.BLOCK_SIZE, s.getRow() * Main.BLOCK_SIZE,
                    Main.BLOCK_SIZE, Main.BLOCK_SIZE
            );
        }

    }

}