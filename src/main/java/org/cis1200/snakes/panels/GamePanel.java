package org.cis1200.snakes.panels;

import org.cis1200.snakes.gameObjects.GameStateConditions;
import org.cis1200.snakes.Helpers;
import org.cis1200.snakes.RunSnakes;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Main mainGame;

    CardLayout cl;
    JPanel container;

    public JLabel scoreLabel;
    JButton pauseButton;
    JButton playButton;
    JButton resetButton;
    JButton saveButton;

    public GamePanel(CardLayout cl, JPanel container) {
        this.cl = cl;
        this.container = container;

        this.setBackground(new Color(150, 200, 150));

        // STATUS PANEL --------------------------------
        final JPanel status_panel = new JPanel();

        // SCORE LABEL
        final JPanel score_panel = new JPanel();
        scoreLabel = new JLabel("");
        score_panel.add(new JLabel("Score: "));
        score_panel.add(scoreLabel);

        status_panel.add(score_panel);

        // GAME PANEL --------------------------------
        int highScore = Helpers.getHScoreFromFile();
        GameStateConditions gsc = Helpers.getCSVData(RunSnakes.PATH_TO_INIT_DATA, highScore);
        // for (int[] i : gsc.getBodypos()){
        // System.out.println("new it");
        // for (int a : i){
        // System.out.println(a);
        // }
        // }
        mainGame = new Main(this, gsc);

        // CONTROL PANEL --------------------------------
        final JPanel control_panel = new JPanel();

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> pauseButton());

        playButton = new JButton("Play");
        playButton.addActionListener(e -> playButton());
        playButton.setVisible(false);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetButton(cl, container));
        resetButton.setVisible(false);

        saveButton = new JButton("Save and quit");
        saveButton.addActionListener(e -> saveButton(cl, container));
        saveButton.setVisible(false);

        control_panel.add(pauseButton);
        control_panel.add(playButton);
        control_panel.add(resetButton);
        control_panel.add(saveButton);

        this.add(control_panel, BorderLayout.NORTH);
        this.add(mainGame, BorderLayout.CENTER);
        this.add(status_panel, BorderLayout.SOUTH);

    }

    public void setSnake(int st) {
        mainGame.setSnake(st);
    }

    public void pauseButton() {
        playButton.setVisible(true);
        resetButton.setVisible(true);
        saveButton.setVisible(true);
        pauseButton.setVisible(false);
        this.mainGame.pause();
    }

    public void playButton() {
        playButton.setVisible(false);
        resetButton.setVisible(false);
        saveButton.setVisible(false);
        pauseButton.setVisible(true);
        this.mainGame.play();
    }

    public void resetButton(CardLayout cl, JPanel container) {
        reset();
        RunSnakes.rerun();
        cl.show(container, "entry");
    }

    public void saveButton(CardLayout cl, JPanel container) {
        mainGame.saveAndQuit();
        RunSnakes.rerun();
        cl.show(container, "entry");
    }

    public void load(GameStateConditions gsc) {
        mainGame.reset(gsc);
    }

    public void reset() {
        int highScore = Helpers.getHScoreFromFile();
        GameStateConditions gsc = Helpers.getCSVData(RunSnakes.PATH_TO_INIT_DATA, highScore);
        mainGame.reset(gsc);
    }

    public void gameover() {
        pauseButton.setVisible(false);
        resetButton.setVisible(true);

    }

}
