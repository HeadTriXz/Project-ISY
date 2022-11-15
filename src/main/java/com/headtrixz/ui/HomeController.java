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
     * Validates the text-fields
     */
    public void validate() {
        boolean bool = validator.validate();
        playTicTacToeButton.setDisable(bool);
        playOthelloButton.setDisable(bool);
        playTournamentButton.setDisable(bool);
    }
}
