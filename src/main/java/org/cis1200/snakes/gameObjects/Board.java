package org.cis1200.snakes.gameObjects;

import org.cis1200.snakes.RunSnakes;

import java.awt.*;

public class Board {

    private int numCol;
    private int numRow;

    private RunSnakes.SquareUnit[][] grid;

    public Board(int w, int h) {
        this.numCol = w;
        this.numRow = h;

        this.grid = new RunSnakes.SquareUnit[this.numRow][this.numCol];

        for (int i = 0; i < this.numRow; i++) {
            for (int j = 0; j < this.numCol; j++) {
                this.grid[i][j] = new RunSnakes.SquareUnit(i, j);
            }
        }
    }

    public void print() {
        for (int i = 0; i < this.numRow; i++) {
            for (int j = 0; j < this.numCol; j++) {
                System.out.println(this.grid[i][j].getX() + " , " + this.grid[i][j].getY());
            }
            System.out.println("\n");
        }
    }

    public void draw(Graphics g) {

        g.setColor(Color.BLACK);

        for (RunSnakes.SquareUnit[] sarr : grid) {
            for (RunSnakes.SquareUnit s : sarr) {
                s.draw(g);
            }
        }

    }
}
