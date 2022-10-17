package com.headtrixz.ui;

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
        // TODO: Find another way to do this a bit more dry
        Stage screen = (Stage) username.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("game-mode.fxml"));
        screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
    }
}
