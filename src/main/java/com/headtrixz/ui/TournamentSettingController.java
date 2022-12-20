package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import com.headtrixz.ui.helpers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the tournament settings page.
 */
public class TournamentSettingController {
    @FXML
    private Button connectButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label ipLabel;
    @FXML
    private Label portLabel;
    @FXML
    private Label messageLabel;

    private Validator validator;

    /**
     * On click event for when the back home button is pressed.
     */
    public void back() {
        this.saveAndSwitch("home", false);
    }

    /**
     * Makes the connection the server.
     */
    private boolean connect() {
        Connection connection = Connection.getInstance();
        String host = UIManager.getSetting("ip");
        int port = Integer.parseInt(UIManager.getSetting("port"));

        this.message("Connecting...");
        boolean hasConnected = connection.connect(host, port);
        if (!hasConnected) {
            this.message("Could not connect to the server.", true);
        }

        return hasConnected;
    }

    /**
     * FXML init method. Makes sure that the settings are persistence between
     * launches.
     */
    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
        ipField.setText(UIManager.getSetting("ip"));
        portField.setText(UIManager.getSetting("port"));

        validator = new Validator();

        validator.setField(usernameField, Validator.USERNAME_PATTERN, usernameLabel);
        validator.setField(portField, Validator.PORT_PATTERN, portLabel);

        validator.validate();

        usernameLabel.setText("Maximaal 16 karakters minimaal 1 en geen . , _");
        ipLabel.setText("Alleen een IP");
        portLabel.setText("Alleen cijfers van 0 - 65535");
    }

    /**
     * Displays a message on the GUI.
     *
     * @param message the message that must be displayed as a string.
     */
    public void message(String message) {
        this.message(message, false);
    }

    /**
     * Displays a message on the GUI.
     *
     * @param message the message that must be displayed as a string.
     * @param failure is boolean set is to true when the text must be red.
     */
    public void message(String message, boolean failure) {
        messageLabel.setText(message);
        if (failure) {
            messageLabel.setStyle("-fx-text-fill: #ff0000");
        }
    }

    /**
     * On click event for when the connect button is pressed.
     */
    public void onConnect() {
        this.saveAndSwitch("tournament", true);
    }

    /**
     * Saves all the settings and switches from screen.
     *
     * @param name The screen to switch to.
     */
    private void saveAndSwitch(String name, boolean connect) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.setSetting("ip", ipField.getText());
        UIManager.setSetting("port", portField.getText());

        boolean hasConnected = false;
        if (connect) {
            hasConnected = this.connect();
        }

        if (!connect || hasConnected) {
            UIManager.switchScreen(name);
        }
    }

    /**
     * Validates the text-fields.
     */
    public void validate() {
        boolean isValid = validator.validate();
        connectButton.setDisable(isValid);
    }
}
