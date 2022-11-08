package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeController {
    public Button playTicTacToeButton;
    @FXML
    private TextField usernameField;
    private String username;

    @FXML
    public void initialize() {
        username = UIManager.getSetting("username");

        usernameField.setText(username);
    }

    private void saveAndSwitch(String name) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.switchScreen(name);
    }

    public void playTicTacToe(){
        saveAndSwitch("game-mode");
    }

    public void playTournament(){
        saveAndSwitch("tournament-setting");
    }
}
