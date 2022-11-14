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

    Validator validator;

    public void initialize() {
        usernameField.setText(UIManager.getSetting("username"));
        
        validator = new Validator();
        validator.setField(usernameField, Validator.usernamePattern);
        validator.attachButtons(playTicTacToeButton, playOthelloButton, playTournamentButton);
    }

    private void saveAndSwitch(String name) {
        UIManager.setSetting("username", usernameField.getText());
        UIManager.switchScreen(name);
    }

    public void playTicTacToe(){
        saveAndSwitch("game");
    }

    public void playTournament(){
        saveAndSwitch("tournament-setting");
    }

    public void validate(){
        String pattern = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-z0-9._]+(?<![_.])$";
        System.out.print(usernameField.getText().matches(pattern));
        System.out.print(" " + usernameField.getText());
        System.out.println();

        /*
        ^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$
         └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
               │         │         │            │           no _ or . at the end
               │         │         │            │
               │         │         │            allowed characters
               │         │         │
               │         │         no __ or _. or ._ or .. inside
               │         │
               │         no _ or . at the beginning
               │
               username is 8-20 characters long
         */

        disableButtons(usernameField.getText().matches(pattern));
    }

    public void disableButtons(boolean disable){
        playTicTacToeButton.setDisable(!disable);
        playOthelloButton.setDisable(!disable);
        playTournamentButton.setDisable(!disable);
    }
}
