package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMode implements Initializable {
    @FXML
    private Text gameType;

    @FXML
    private Button onlineGame;

    @FXML
    private Button tournament;

    public void playAI() throws Exception {
        UIManager.switchScreen("game");
    }

    public void playTournament() {
        UIManager.switchScreen("tournament");
    }

    public void goHome() throws Exception {
        UIManager.switchScreen("home");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // gameType.setText("Speel " + url);
    }
}
