package org.cis1200.snakes;

// imports necessary libraries for Java swing

import org.cis1200.snakes.panels.*;

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnakes implements Runnable {
    GamePanel gamePanel;
    static EntryPanel entryPanel;
    ChooseModePanel chooseModePanel;
    JPanel instructionsPanel;

    public static final String PATH_TO_INIT_DATA = "files/init.csv";
    public static final String PATH_TO_HIGUEST_SCORE = "files/hs.txt";
    public static final String PATH_TO_SAVED_DATA = "files/saved.csv";

    public void run() {
        JFrame frame = new JFrame("Snakes");
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setLocation(1000, 300);

        // CREATE CARD CONTAINER
        JPanel container = new JPanel();
        CardLayout cl = new CardLayout();
        container.setLayout(cl);

        // CREATE PANELS FROM CLASSES
        gamePanel = new GamePanel(cl, container);

        entryPanel = new EntryPanel(
                cl,
                container, gamePanel
        );

        chooseModePanel = new ChooseModePanel(cl, container, gamePanel);

        instructionsPanel = new InstructionsPanel(cl, container, gamePanel);

        // ADD PANELS TO CONTAINER
        container.add(entryPanel, "entry");
        container.add(chooseModePanel, "mode");
        container.add(instructionsPanel, "inst");
        container.add(gamePanel, "game");

        // ADD CONTAINER TO FRAME
        cl.show(container, "entry");

        frame.add(container);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void rerun() {
        entryPanel.rerun();
    }

    public static class SquareUnit {
        private int row;
        private int col;

        private int x;
        private int y;

        public SquareUnit(int r, int c) {
            this.row = r;
            this.col = c;
            this.y = r * Main.BLOCK_SIZE;
            this.x = c * Main.BLOCK_SIZE;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setCol(int col) {
            this.col = col;
            this.x = col * Main.BLOCK_SIZE;
        }

        public void setRow(int row) {
            this.row = row;
            this.y = row * Main.BLOCK_SIZE;
        }

        public void addToRow(int add) {
            setRow(this.row += add);
        }

        public void addToCol(int add) {
            setCol(this.col += add);
        }

        public void draw(Graphics g) {
            g.drawRect(this.x, this.y, Main.BLOCK_SIZE, Main.BLOCK_SIZE);

        }
    }
}