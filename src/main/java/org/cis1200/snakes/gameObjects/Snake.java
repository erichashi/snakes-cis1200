package org.cis1200.snakes.gameObjects;

import org.cis1200.snakes.RunSnakes;
import org.cis1200.snakes.panels.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Snake is the player
 */
public class Snake {
    // public static final int SIZE = 20;

    private RunSnakes.SquareUnit head;
    private LinkedList<RunSnakes.SquareUnit> body;

    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int RIGHT = 1;
    public static final int LEFT = -1;

    private int vdir; // vertical dir
    private int hdir; // horizontal dir
    private boolean collide;
    private int len;

    private Color color;

    public static final Color STANDARD_SNAKE_COLOR = Color.green;

    public static final Color HEAD_COLOR = Color.orange;
    private int type;
    private int[] nextMove;

    public Snake(int row, int col, int hdir, int vdir, int[][] bodypos) {
        this.head = new RunSnakes.SquareUnit(row, col);

        // Body positions
        this.body = new LinkedList<>();
        this.body.add(head);

        // skip 0th because it is head
        for (int i = 1; i < bodypos.length; i++) {
            RunSnakes.SquareUnit tl;
            if (bodypos[i][0] == 1) {
                tl = new Hole(
                        bodypos[i][1],
                        bodypos[i][2]
                );
            } else {
                tl = new RunSnakes.SquareUnit(
                        bodypos[i][1],
                        bodypos[i][2]
                );
            }
            this.body.add(tl);
        }

        // Direction
        this.vdir = vdir;
        this.hdir = hdir;
        this.nextMove = new int[] { hdir, vdir };

        this.collide = false;
        this.len = 1;

        this.color = STANDARD_SNAKE_COLOR;
        this.type = Main.STANDARD_SNAKE;
    }

    public RunSnakes.SquareUnit getHead() {
        return head;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setVdir(int dir) {
        this.vdir = dir;
    }

    public void setHdir(int dir) {
        this.hdir = dir;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getHdir() {
        return hdir;
    }

    public int getVdir() {
        return vdir;
    }

    public Color getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LinkedList<RunSnakes.SquareUnit> getBody() {
        return body;
    }

    public void printBody() {
        for (RunSnakes.SquareUnit b : this.getBody()) {
            System.out.println(b.getRow() + ", " + b.getCol());
        }
    }

    public boolean collided() {
        return collide;
    }

    public void applyNextMove() {

    }

    public void handleEvent(int e) {
        if (e == KeyEvent.VK_UP && this.vdir != DOWN) {
            this.setVdir(UP);
            this.setHdir(0);
        } else if (e == KeyEvent.VK_DOWN && this.vdir != UP) {
            this.setVdir(DOWN);
            this.setHdir(0);
        } else if (e == KeyEvent.VK_RIGHT && this.hdir != LEFT) {
            this.setHdir(RIGHT);
            this.setVdir(0);
        } else if (e == KeyEvent.VK_LEFT && this.hdir != RIGHT) {
            this.setHdir(LEFT);
            this.setVdir(0);
        }
    }

    public boolean checkHeadCollision(RunSnakes.SquareUnit b, int[] futureHeadPos) {
        return b.getRow() == futureHeadPos[0] && b.getCol() == futureHeadPos[1];

    }

    public void move() {

        applyNextMove();

        // Pos of head is updated after the body; saving the future position to avail
        // the loop to check collision,
        // without the need of iterating again
        int[] futureHeadPos = { this.head.getRow() + this.vdir, this.head.getCol() + this.hdir };

        // Update pos of body
        // snake is like [h][b][b][b][b]. This loop runs from the tail to the head and
        // updates the position of the current node to the prev node

        ListIterator<RunSnakes.SquareUnit> bodyIt = this.body.listIterator();
        // Traverse the list from the tail to head
        while (bodyIt.hasNext()) {
            bodyIt.next();
        }
        bodyIt.previous();

        // from the end to the start
        while (bodyIt.hasPrevious()) {
            // System.out.println("-----NEW ITERATION-----");

            RunSnakes.SquareUnit prevb = bodyIt.previous();

            // System.out.println("prev is: " +prevb.getRow()+" ,"+prevb.getCol());

            // jump two to get the current
            bodyIt.next();
            RunSnakes.SquareUnit b = bodyIt.next();
            // System.out.println("curr is: " +b.getRow()+" ,"+b.getCol());

            b.setCol(prevb.getCol());
            b.setRow(prevb.getRow());

            // Check collision with head:
            if (checkHeadCollision(b, futureHeadPos)) {
                this.collide = true;
                break;
            }

            // Update While loop
            bodyIt.previous();
            bodyIt.previous();

        }

        // Move Head
        this.head.addToRow(this.vdir);
        this.head.addToCol(this.hdir);

        // If reach up or bottom bounds, change pos
        if (this.head.getRow() < 0) {
            this.head.setRow(Main.BOARD_HEIGHT - 1);
        } else if (this.head.getRow() > Main.BOARD_HEIGHT - 1) {
            this.head.setRow(0);
        }
        // right and left bounds
        if (this.head.getCol() > Main.BOARD_WIDTH - 1) {
            this.head.setCol(0);
        } else if (this.head.getCol() < 0) {
            this.head.setCol(Main.BOARD_WIDTH - 1);

        }
    }

    public void update() {
        move();
    }

    public void increase() {
        RunSnakes.SquareUnit s = new RunSnakes.SquareUnit(this.head.getRow(), this.head.getCol());
        this.body.add(s);
        this.len++;

    }

    public void drawBody(Graphics g) {
        g.setColor(this.color);

        for (RunSnakes.SquareUnit s : this.body) {
            g.fillRect(
                    s.getCol() * Main.BLOCK_SIZE, s.getRow() * Main.BLOCK_SIZE,
                    Main.BLOCK_SIZE, Main.BLOCK_SIZE
            );
        }
    }

    public void draw(Graphics g) {
        drawBody(g);
        g.setColor(HEAD_COLOR);
        g.fillRect(
                this.getHead().getCol() * Main.BLOCK_SIZE,
                this.getHead().getRow() * Main.BLOCK_SIZE,
                Main.BLOCK_SIZE, Main.BLOCK_SIZE
        );
    }
}