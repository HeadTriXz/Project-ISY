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
        // TODO: Find another way to do this a bit more dry
        Stage screen = (Stage) gameType.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("game.fxml"));
        screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
    }
}
// mvn clean compile exec:java
