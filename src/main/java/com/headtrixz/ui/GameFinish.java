package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GameFinish {
    @FXML
    private Text endText;

    public void goHome() throws Exception {
        UIManager.switchScreen("home");
    }
}
