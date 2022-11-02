package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Home {
    @FXML
    private TextField username;

    @FXML
    private TextField address;

    @FXML
    private TextField port;

    @FXML
    private Text connectionMessage;

    public void playTicTacToe() throws Exception {
        UIManager.switchScreen("game-mode");
    }
}
