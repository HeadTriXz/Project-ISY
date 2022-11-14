package com.headtrixz.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    private TextField usernameField;

    /**
     * FXML init method. Gets called when the screen has loaded.
     * Sets the saved username to the text field.
     */
    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
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
}
