package com.headtrixz.ui;

import com.headtrixz.ui.elements.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    private TextField usernameField;

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
        validator.setField(usernameField, Validator.USERNAME_PATTERN);
        validator.attachButtons(playTicTacToeButton, playOthelloButton, playTournamentButton);
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
        validator.validate();
    }

    public void disableButtons(boolean disable) {
        playTicTacToeButton.setDisable(!disable);
        playOthelloButton.setDisable(!disable);
        playTournamentButton.setDisable(!disable);
    }
}
