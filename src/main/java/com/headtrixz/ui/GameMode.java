package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GameMode {
    @FXML
    private Text gameType;

    @FXML
    private Button onlineGame;

    @FXML
    private Button tournament;

    public void playAI() throws Exception {
        UIManager.switchScreen("game");
    }

    public void goHome() throws Exception {
        UIManager.switchScreen("home");
    }
}
