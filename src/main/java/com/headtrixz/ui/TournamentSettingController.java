package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import com.headtrixz.ui.helpers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label usernameLabel;
    @FXML
    private Label ipLabel;
    @FXML
    private Label portLabel;
    @FXML
    private Label messageLabel;

    private Validator validator;

    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
        ipField.setText(UIManager.getSetting("ip"));
        portField.setText(UIManager.getSetting("port"));

        validator = new Validator();

        validator.setField(usernameField, Validator.USERNAME_PATTERN, usernameLabel);
        validator.setField(ipField, Validator.IP_PATTERN, ipLabel);
        validator.setField(portField, Validator.PORT_PATTERN, portLabel);

        validator.validate();
    }

    public void connect() throws NumberFormatException {
        Connection conn = Connection.getInstance();

        message("connecting");
        try {
            conn.connect(UIManager.getSetting("ip"), Integer.parseInt(UIManager.getSetting("port")));
        } catch (Exception e) {
            message("Whoops cannot connect.", true);
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

    /**
     * Validates the text-fields
     */
    public void validate() {
        boolean bool = validator.validate();
        playTicTacToeButton.setDisable(bool);
//        playOthelloButton.setDisable(bool);
    }

    public void message(String mess) {
        message(mess, false);
    }

    public void message(String mess, boolean failure) {
        messageLabel.setText(mess);
        if (failure)
            messageLabel.setStyle("-fx-text-fill: #ff0000");
    }
}
