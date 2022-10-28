package com.headtrixz.ui;

import com.headtrixz.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        Utils.goTo(connectionMessage, "game-mode.fxml");
    }
}
