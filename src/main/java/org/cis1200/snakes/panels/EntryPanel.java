package org.cis1200.snakes.panels;

import org.cis1200.snakes.gameObjects.GameStateConditions;
import org.cis1200.snakes.Helpers;
import org.cis1200.snakes.RunSnakes;

import javax.swing.*;
import java.awt.*;

public class EntryPanel extends JPanel {
    private final JButton loadButton;
    private final JLabel hsLabel;
    private GameStateConditions data;

    public EntryPanel(
            CardLayout cl, JPanel container,
            GamePanel gp
    ) {

        this.setBackground(new Color(150, 200, 150));

        GridLayout l = new GridLayout(0, 1);
        this.setLayout(l);

        JLabel snakesL = new JLabel("SNAKES");
        snakesL.setFont(new Font("", Font.BOLD, 30));
        snakesL.setHorizontalAlignment(JLabel.CENTER);
        this.add(snakesL);

        JButton chooseGameModeButton = new JButton("Play");
        chooseGameModeButton.setFont(new Font("", Font.BOLD, 20));
        chooseGameModeButton.setPreferredSize(new Dimension(50, 30));
        chooseGameModeButton.addActionListener(e -> {
            cl.show(container, "mode");
        });
        this.add(chooseGameModeButton);

        loadButton = new JButton("Load Saved");
        loadButton.setFont(new Font("", Font.BOLD, 20));
        loadButton.addActionListener(e -> {
            gp.load(this.data);
            cl.show(container, "game");
            gp.playButton();
        });
        loadButton.setVisible(false);
        this.add(loadButton);

        hsLabel = new JLabel();
        hsLabel.setFont(new Font("", Font.BOLD, 20));
        hsLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(hsLabel);

        rerun();

    }

    public void rerun() {

        int hs = Helpers.getHScoreFromFile();

        boolean load = false;
        try {
            this.data = Helpers.getCSVData(RunSnakes.PATH_TO_SAVED_DATA, hs);
            load = true;
        } catch (RuntimeException e) {
        }

        loadButton.setVisible(load);

        hsLabel.setText("High Score: " + hs);
    }

}
