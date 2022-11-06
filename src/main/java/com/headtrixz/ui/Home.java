package com.headtrixz.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.TreeMap;
import java.util.prefs.Preferences;

public class Home {
    public Button playTicTacToeButton;
    public Button connect;
    public Button save;
    @FXML
    private TextField username;

    @FXML
    private TextField address;

    @FXML
    private TextField port;

    @FXML
    private Text connectionMessage;

    private Boolean Tyfus = false;
    private String usernameLocal;
    private String addressLocal;
    private String portLocal;

    @FXML
    public void initialize() {
        usernameLocal = UIManager.getSetting("username");
        addressLocal = UIManager.getSetting("address");
        portLocal = UIManager.getSetting("port");

        username.setText(usernameLocal);
        address.setText(addressLocal);
        port.setText(portLocal);

        disableCheck();
    }

    public void playTicTacToe() throws Exception {
        UIManager.switchScreen("game-mode");
    }

    public void connect() {
        if (Tyfus) {
            save.setDisable(false);
            connect.setText("Connected");
            Tyfus = false;
        } else {
            save.setDisable(true);
            connect.setText("Disconnect");

            Tyfus = true;
        }
    }

    public void save() {
        UIManager.setSetting("username", username.getText());
        UIManager.setSetting("address", address.getText());
        UIManager.setSetting("port", port.getText());

        usernameLocal = username.getText();
        addressLocal = address.getText();
        portLocal = port.getText();


        connect.setDisable(false);

        disableCheck();
    }

    public void disableCheck() {
        boolean boolForSinglePlayer = !usernameLocal.equals("");
        boolean boolForMultiplayer = !usernameLocal.equals("") && !addressLocal.equals("") && !portLocal.equals("");
        
        connect.setDisable(!boolForMultiplayer);
        playTicTacToeButton.setDisable(!boolForSinglePlayer);

    }
}
