package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    private TextField usernameField;

    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
    }

    private void saveAndSwitch(String name) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.switchScreen(name);
    }

    public void playTicTacToe(){
        saveAndSwitch("game");
    }

    public void playTournament(){
        saveAndSwitch("tournament-setting");
    }
}
