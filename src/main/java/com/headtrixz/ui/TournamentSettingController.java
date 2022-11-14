package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import com.headtrixz.ui.elements.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TournamentSettingController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Button playTicTacToeButton;
    @FXML
    private Button playOthelloButton;

    Validator validator;

    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
        ipField.setText(UIManager.getSetting("ip"));
        portField.setText(UIManager.getSetting("port"));

        validator = new Validator();

        validator.setField(usernameField, Validator.usernamePattern);
        validator.setField(ipField, Validator.ipPattern);
        validator.setField(portField, Validator.portPattern);

        validator.attachButtons(playTicTacToeButton, playOthelloButton);
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

    public void back() {
        saveAndSwitch("home");
    }

    public void playTicTacToe() {
        connect();
        // TODO: response when connected or not!
        saveAndSwitch("tournament");
    }

    public void playOthello() {
        connect();
        // TODO: response when connected or not!
        // saveAndSwitch("othello");
    }

    private void saveAndSwitch(String name) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.setSetting("ip", ipField.getText());
        UIManager.setSetting("port", portField.getText());

        UIManager.switchScreen(name);
    }

    public void validate() {
        validator.validate();
    }

    public void disableButtons(boolean disable) {
        playTicTacToeButton.setDisable(!disable);
        playOthelloButton.setDisable(!disable);
    }
}
