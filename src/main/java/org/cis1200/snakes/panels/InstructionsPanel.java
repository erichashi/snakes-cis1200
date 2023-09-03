package org.cis1200.snakes.panels;

import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel {

    public InstructionsPanel(CardLayout cl, JPanel container, GamePanel gp) {

        this.setBackground(new Color(150, 200, 150));

        GridLayout l = new GridLayout(0, 1);
        this.setLayout(l);

        JLabel arrowL = new JLabel(
                "<html>Use the arrows to move the snake, and don't touch you own body!. </html>"
        );
        arrowL.setFont(new Font("", Font.BOLD, 20));
        arrowL.setHorizontalAlignment(JLabel.CENTER);
        this.add(arrowL);

        JLabel portalL = new JLabel(
                "<html>If your mode is Portal, use SPACE to create a portal at " +
                        "the tail, and then space again to teleport to the created portal</html>"
        );
        portalL.setFont(new Font("", Font.BOLD, 20));
        portalL.setHorizontalAlignment(JLabel.CENTER);
        this.add(portalL);

        JLabel holeL = new JLabel("<html>If your mode is Hole, you can pass through the </html>");
        holeL.setFont(new Font("", Font.BOLD, 20));
        holeL.setHorizontalAlignment(JLabel.CENTER);
        this.add(holeL);

        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("", Font.BOLD, 20));
        playButton.setPreferredSize(new Dimension(50, 30));

        this.add(playButton);

        playButton.addActionListener(e -> {
            cl.show(container, "game");
            gp.playButton();
        });

        this.add(playButton);
    }

}
