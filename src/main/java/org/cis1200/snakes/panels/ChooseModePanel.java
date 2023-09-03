package org.cis1200.snakes.panels;

import javax.swing.*;
import java.awt.*;

public class ChooseModePanel extends JPanel {

    private int[] snakeType;

    public ChooseModePanel(CardLayout cl, JPanel container, GamePanel gp) {

        this.setBackground(new Color(150, 200, 150));

        GridLayout l = new GridLayout(0, 2);
        this.setLayout(l);

        this.snakeType = new int[] { 0 };

        JButton chooseNormalButton = new JButton("Classic");
        chooseNormalButton.addActionListener(e -> {
            snakeType[0] = 0;
            gp.setSnake(snakeType[0]);
            cl.show(container, "inst");
        });
        this.add(chooseNormalButton);
        JLabel classicL = new JLabel(
                "The classic " +
                        "version of snake"
        );
        classicL.setFont(new Font("", Font.BOLD, 15));
        classicL.setHorizontalAlignment(JLabel.CENTER);
        this.add(classicL);

        JButton choosePortalButton = new JButton("Portals");
        choosePortalButton.addActionListener(e -> {
            snakeType[0] = 1;
            gp.setSnake(snakeType[0]);
            cl.show(container, "inst");
        });
        this.add(choosePortalButton);
        JLabel portalL = new JLabel(
                "Create portals in " +
                        "the board"
        );
        portalL.setFont(new Font("", Font.BOLD, 15));
        portalL.setHorizontalAlignment(JLabel.CENTER);
        this.add(portalL);

        JButton chooseHoleButton = new JButton("Holes");
        chooseHoleButton.addActionListener(e -> {
            snakeType[0] = 2;
            gp.setSnake(snakeType[0]);
            cl.show(container, "inst");
        });
        this.add(chooseHoleButton);

        JPanel holesLs = new JPanel();
        holesLs.setBackground(new Color(150, 200, 150));
        JLabel holeL1 = new JLabel(
                "A snake with holes " +
                        "in"
        );
        JLabel holeL2 = new JLabel(
                "which you can pass " +
                        "through"
        );
        holeL1.setFont(new Font("", Font.BOLD, 10));
        holeL2.setFont(new Font("", Font.BOLD, 10));
        holeL1.setHorizontalAlignment(JLabel.CENTER);
        holeL2.setHorizontalAlignment(JLabel.CENTER);

        holesLs.add(holeL1);
        holesLs.add(holeL2);

        this.add(holesLs);

    }

    public int getSnakeType() {
        return snakeType[0];
    }
}
