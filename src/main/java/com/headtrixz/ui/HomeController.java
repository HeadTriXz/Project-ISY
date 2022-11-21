package com.headtrixz.ui;

import com.headtrixz.ui.helpers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameLabel;

    @FXML
    private Button playTicTacToeButton;
    @FXML
    private Button playOthelloButton;
    @FXML
    private Button playTournamentButton;

    private Validator validator;

    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));

        validator = new Validator();
        validator.setField(usernameField, Validator.USERNAME_PATTERN, usernameLabel);
        validator.validate();

        usernameLabel.setText("Maximaal 16 karakters minimaal 1 en geen . , _");
    }

    private void saveAndSwitch(String name) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.switchScreen(name);
    }

    public void playTicTacToe() {
        saveAndSwitch("game");
    }

    public void playTournament() {
        saveAndSwitch("tournament-setting");
    }

    /**
     * Validates the text-fields.
     */
    public void validate() {
        boolean isValid = validator.validate();
        playTicTacToeButton.setDisable(isValid);
        playOthelloButton.setDisable(isValid);
        playTournamentButton.setDisable(isValid);
    }
}
