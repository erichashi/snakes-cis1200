package org.cis1200.snakes.gameObjects;

import org.cis1200.snakes.RunSnakes;
import org.cis1200.snakes.panels.Main;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PortalSnake extends Snake {
    /**
     *
     * @param row
     * @param col
     * @param hdir
     * @param vdir
     * @param bodypos
     */

    private RunSnakes.SquareUnit portal;
    private RunSnakes.SquareUnit tail;
    private boolean portalActive;

    public static final Color PORTAL_SNAKE_COLOR = new Color(126, 63, 143);
    public static final Color PORTAL_COLOR = Color.blue;

    public PortalSnake(
            int row, int col, int hdir,
            int vdir, int[][] bodypos,
            int[] portalpos
    ) {
        super(row, col, hdir, vdir, bodypos);

        this.setColor(PORTAL_SNAKE_COLOR);

        if (portalpos[0] == -1) {
            this.portalActive = false;
            this.portal = null;
        } else {
            this.portalActive = true;
            this.portal = new RunSnakes.SquareUnit(portalpos[0], portalpos[1]);
        }

        this.setType(Main.PORTAL_SNAKE);
    }

    public boolean isPortalActive() {
        return portalActive;
    }

    public RunSnakes.SquareUnit getTail() {
        return this.tail;
    }

    public RunSnakes.SquareUnit getPortal() {
        return portal;
    }

    public void findTail() {
        this.tail = this.getBody().getLast();
    }

    @Override
    public void handleEvent(int e) {
        if (e == KeyEvent.VK_UP && this.getVdir() != DOWN) {
            this.setVdir(UP);
            this.setHdir(0);
        } else if (e == KeyEvent.VK_DOWN && this.getVdir() != UP) {
            this.setVdir(DOWN);
            this.setHdir(0);
        } else if (e == KeyEvent.VK_RIGHT && this.getHdir() != LEFT) {
            this.setHdir(RIGHT);
            this.setVdir(0);
        } else if (e == KeyEvent.VK_LEFT && this.getHdir() != RIGHT) {
            this.setHdir(LEFT);
            this.setVdir(0);
        } else if (e == KeyEvent.VK_SPACE) {
            if (portalActive) {
                teleport();
            } else {
                createPortal();
            }
        }
    }

    public void teleport() {
        this.getHead().setRow(this.portal.getRow());
        this.getHead().setCol(this.portal.getCol());

        this.portalActive = false;
        this.portal = null;

    }

    public void update() {
        move();
    }

    public void createPortal() {
        this.portalActive = true;
        this.findTail();
        this.portal = new RunSnakes.SquareUnit(this.tail.getRow(), this.tail.getCol());
    }

    public void draw(Graphics g) {
        drawBody(g);

        g.setColor(HEAD_COLOR);
        g.fillRect(
                this.getHead().getCol() * Main.BLOCK_SIZE,
                this.getHead().getRow() * Main.BLOCK_SIZE,
                Main.BLOCK_SIZE, Main.BLOCK_SIZE
        );

        if (this.portalActive) {
            g.setColor(PORTAL_COLOR);
            g.fillRect(
                    this.portal.getCol() * Main.BLOCK_SIZE, this.portal.getRow() * Main.BLOCK_SIZE,
                    Main.BLOCK_SIZE, Main.BLOCK_SIZE
            );
        }
    }

}
