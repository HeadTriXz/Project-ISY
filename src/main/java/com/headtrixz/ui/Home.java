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

    private Boolean _connected = false;
    private String _username;
    private String _address;
    private String _port;

    @FXML
    public void initialize() {
        _username = UIManager.getSetting("username");
        _address = UIManager.getSetting("address");
        _port = UIManager.getSetting("port");

        username.setText(_username);
        address.setText(_address);
        port.setText(_port);

        disableCheck();
    }

    public void playTicTacToe() throws Exception {
        UIManager.switchScreen("game-mode");
    }

    public void connect() {
        if (_connected) {
            save.setDisable(false);
            connect.setText("Connected");
            _connected = false;
        } else {
            save.setDisable(true);
            connect.setText("Disconnect");

            _connected = true;
        }
    }

    public void save() {
        UIManager.setSetting("username", username.getText());
        UIManager.setSetting("address", address.getText());
        UIManager.setSetting("port", port.getText());

        _username = username.getText();
        _address = address.getText();
        _port = port.getText();


        connect.setDisable(false);

        disableCheck();
    }

    public void disableCheck() {
        boolean boolForSinglePlayer = !_username.equals("");
        boolean boolForMultiplayer = !_username.equals("") && !_address.equals("") && !_port.equals("");
        
        connect.setDisable(!boolForMultiplayer);
        playTicTacToeButton.setDisable(!boolForSinglePlayer);

    }
}
