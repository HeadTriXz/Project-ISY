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
        Connection conn = Connection.getInstance();

        this.message("connecting");
        try {
            conn.connect(UIManager.getSetting("ip"), Integer.parseInt(UIManager.getSetting("port")));
        } catch (Exception e) {
            this.message("Whoops cannot connect.", true);
            e.printStackTrace();
        }
    }

    /**
     * On click event for when the back home button is pressed.
     */
    public void back() {
        saveAndSwitch("home", false);
    }

    /**
     * On click event for when the TTT button is pressed.
     */
    public void playTicTacToe() {
        saveAndSwitch("tournament", true);
    }

    /**
     * On click event for when the Othello button is pressed.
     */
    public void playOthello() {
        // saveAndSwitch("othello", true);
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

        if (connect) {
            this.connect();
        }

        UIManager.switchScreen(name);
    }

    /**
     * Validates the text-fields.
     */
    public void validate() {
        boolean isValid = validator.validate();
        playTicTacToeButton.setDisable(isValid);
//        playOthelloButton.setDisable(isValid);
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
     * @param mess the message that must be displayed as a string.
     * @param failure is boolean set is to true when the text must be red.
     */
    public void message(String mess, boolean failure) {
        messageLabel.setText(mess);
        if (failure) {
            messageLabel.setStyle("-fx-text-fill: #ff0000");
        }
    }
}
