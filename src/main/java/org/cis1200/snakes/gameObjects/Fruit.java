package org.cis1200.snakes.gameObjects;

import org.cis1200.snakes.RunSnakes;
import org.cis1200.snakes.panels.Main;

import java.awt.*;

public class Fruit extends RunSnakes.SquareUnit {
    public Fruit(int r, int c) {
        super(r, c);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);

        g.fillRect(
                this.getCol() * Main.BLOCK_SIZE, this.getRow() * Main.BLOCK_SIZE, Main.BLOCK_SIZE,
                Main.BLOCK_SIZE
        );
    }

}
