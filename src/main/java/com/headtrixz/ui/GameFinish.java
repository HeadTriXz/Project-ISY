package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameFinish {
    @FXML
    private Text endText;

    public void goHome() throws Exception {
        Stage screen = (Stage) endText.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("home.fxml"));
        screen.setScene(new Scene(fxmlLoader.load(), 600, 400));
    }
}
