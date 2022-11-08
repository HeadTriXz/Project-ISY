package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class TournamentSettingController {
    public Button playTicTacToeButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;

    @FXML
    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
        ipField.setText(UIManager.getSetting("ip"));
        portField.setText(UIManager.getSetting("port"));
    }

    private void save() {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.setSetting("ip", ipField.getText());
        UIManager.setSetting("port", portField.getText());
    }

    public void connect() throws NumberFormatException {
        Connection conn = Connection.getInstance();

        try {
            conn.connect(UIManager.getSetting("ip"), Integer.parseInt(UIManager.getSetting("port")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back() throws Exception {
        save();
        UIManager.switchScreen("home");
    }

    public void playTicTacToe() throws Exception {
        save();
        connect();
        UIManager.switchScreen("tournament");
    }

    public void playOthello() throws Exception {
        save();
        connect();
//        UIManager.switchScreen("othello");
    }

    private boolean validate(){



        return true;
    }
}
