package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Home {
    public Button playTicTacToeButton;
    @FXML
    private TextField username;
    private String usernameLocal;

    @FXML
    public void initialize() {
        usernameLocal = UIManager.getSetting("username");

        username.setText(usernameLocal);
    }

    private void saveAndSwitch(String name) {
        System.out.println(username.getText());
        UIManager.switchScreen(name);
    }

    public void playTicTacToe(){
        saveAndSwitch("game-mode");
    }

    public void playTournament(){
        saveAndSwitch("tournament-setting");
    }
}
