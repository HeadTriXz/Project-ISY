package com.headtrixz.ui;

import com.headtrixz.networking.Connection;
import com.headtrixz.networking.InputListener;
import com.headtrixz.networking.ServerMessageType;
import com.headtrixz.ui.helpers.Validator;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class controls the input from the GUI.
 */
public class TournamentSettingController {
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
    @FXML
    private Button connectButton;

    private Validator validator;
    private Connection connection;

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
     * Makes the connection the the server.
     */
    private void connect() {
        connection = Connection.getInstance();
        this.message("connecting");
        try {
            connection.connect(UIManager.getSetting("ip"),
                Integer.parseInt(UIManager.getSetting("port")));

            connection.getInputHandler().subscribe(ServerMessageType.PLAYERLIST, onPlayerList);
            connection.getOutputHandler().getPlayerList();
        } catch (Exception e) {
            this.message("Whoops cannot connect.", true);
            e.printStackTrace();
        }
    }

    private final InputListener onPlayerList = message -> {
        Platform.runLater(() -> {
            if (Arrays.asList(message.getArray()).contains(usernameField.getText())) {
                this.message(
                    "Gebruiker met deze naam bestaat al. \n Kies een andere naam.", true);
            } else {
                this.goToTournament();
            }
        });
    };

    /**
     * On click event for when the back home button is pressed.
     */
    public void back() {
        this.save();
        UIManager.switchScreen("home");
    }

    /**
     * On click event for when the TTT button is pressed.
     */
    public void onConnect() {
        this.save();
        this.connect();
    }

    public void goToTournament() {
        connection.getInputHandler().unsubscribe(ServerMessageType.PLAYERLIST, onPlayerList);
        UIManager.switchScreen("tournament");
    }

    /**
     * Saves all the settings.
     */
    private void save() {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.setSetting("ip", ipField.getText());
        UIManager.setSetting("port", portField.getText());
    }

    /**
     * Validates the text-fields.
     */
    public void validate() {
        boolean isValid = validator.validate();
        connectButton.setDisable(isValid);
    }

    /**
     * Displays a message on the GUI.
     *
     * @param mess the message that must be displayed as a string.
     */
    public void message(String mess) {
        this.message(mess, false);
    }

    /**
     * Displays a message on the GUI.
     *
     * @param mess    the message that must be displayed as a string.
     * @param failure is boolean set is to true when the text must be red.
     */
    public void message(String mess, boolean failure) {
        messageLabel.setText(mess);
        if (failure) {
            messageLabel.setStyle("-fx-text-fill: #ff0000");
        }
    }
}
