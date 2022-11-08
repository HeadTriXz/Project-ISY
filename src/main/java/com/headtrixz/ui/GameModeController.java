package com.headtrixz.ui;

import com.headtrixz.networking.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GameModeController {
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

    public void initialize() {
        Connection connection = Connection.getInstance();

        if (!connection.isConnected()) {
            tournament.setDisable(true);
        }
    }
}
