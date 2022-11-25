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

    /**
     * FXML init method. Gets called when the screen has loaded.
     * Sets the saved username to the text field.
     */
    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));

        validator = new Validator();
        validator.setField(usernameField, Validator.USERNAME_PATTERN, usernameLabel);
        validator.validate();

        usernameLabel.setText("Maximaal 16 karakters minimaal 1 en geen . , _");
    }

    /**
     * Saves the username and switches to the next screen.
     *
     * @param name the name of the next screen.
     */
    private void saveAndSwitch(String name) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.switchScreen(name);
    }

    /**
     * On click event for the play tic tac toe button.
     */
    public void playTicTacToe(){
        saveAndSwitch("game");
    }

    /**
     * On click event for the tournament button.
     */
    public void playTournament(){
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
