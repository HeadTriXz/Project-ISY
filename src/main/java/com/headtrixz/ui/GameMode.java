package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMode {
    @FXML
    private Text gameType;

    @FXML
    private Button onlineGame;

    @FXML
    private Button tournament;

    public void playAI() throws Exception {
        this.goTo("game");
    }

    public void goHome() throws Exception {
        this.goTo("home");
    }

    private void goTo(String location) throws Exception {
        Stage screen = (Stage) gameType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/" + location + ".fxml"));
        screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
    }
}
