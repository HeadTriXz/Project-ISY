package com.headtrixz.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.TreeMap;
import java.util.prefs.Preferences;

public class Home {
    public Button playTicTacToeButton;
    @FXML
    private TextField username;

    @FXML
    private TextField address;

    @FXML
    private TextField port;

    @FXML
    private Text connectionMessage;

    @FXML
    public void initialize() {
            username.setText(UIManager.getSetting("username"));
            address.setText(UIManager.getSetting("address"));
            port.setText(UIManager.getSetting("port"));
            }

    public void playTicTacToe() throws Exception {
        UIManager.switchScreen("game-mode");
    }

    public void canPlay(Boolean bool){
        playTicTacToeButton.setDisable(!bool);
    }

    public void connect() {
        UIManager.setSetting("username", username.getText());
        UIManager.setSetting("address", address.getText());
        UIManager.setSetting("port", port.getText());

        canPlay(true);
    }
}
