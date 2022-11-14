package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import com.headtrixz.ui.elements.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    @FXML
    private Text messageText;

    private Validator validator;

    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
        ipField.setText(UIManager.getSetting("ip"));
        portField.setText(UIManager.getSetting("port"));

        validator = new Validator();

        validator.setField(usernameField, Validator.USERNAME_PATTERN);
        validator.setField(ipField, Validator.IP_PATTERN);
        validator.setField(portField, Validator.PORT_PATTERN);

        validator.attachButtons(playTicTacToeButton, playOthelloButton);

        validator.validate();
    }

    public void connect() throws NumberFormatException {
        Connection conn = Connection.getInstance();
        message("Connecting");

        try {
            conn.connect(UIManager.getSetting("ip"), Integer.parseInt(UIManager.getSetting("port")));
        } catch (Exception e) {
            messageFailure("Whoops cannot connect.");
            e.printStackTrace();
        }
    }

    public void back() {
        saveAndSwitch("home", false);
    }

    public void playTicTacToe() {
        saveAndSwitch("tournament", true);
    }

    public void playOthello() {
        // saveAndSwitch("othello", true);
    }

    private void saveAndSwitch(String name, boolean connect) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.setSetting("ip", ipField.getText());
        UIManager.setSetting("port", portField.getText());

        if (connect)
            connect();

        UIManager.switchScreen(name);
    }

    public void validate() {
        validator.validate();
    }

    public void message(String message){
        messageText.setText(message);
    }
    public void messageFailure(String message){
        messageText.setText(message);
        messageText.setStyle("-fx-text-fill: red;");
    }
}
