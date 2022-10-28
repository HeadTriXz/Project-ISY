package com.headtrixz.ui;

import com.headtrixz.Utils;
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

    public void playAI(){
        Utils.goTo(gameType, "home");
    }

    public void goHome(){
        Utils.goTo(gameType, "home");
    }

}
