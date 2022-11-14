package com.headtrixz.ui.elements;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Validator {

    public static final String USERNAME_PATTERN = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-z0-9._]+(?<![_.])$";
    public static final String IP_PATTERN = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
    public static final String PORT_PATTERN = "^[0-9._]+(?<![_.])$";

    private final ArrayList<TextField> textFields;
    private final ArrayList<String> patterns;

    private Button[] buttons;

    public Validator() {
        textFields = new ArrayList<>();
        patterns = new ArrayList<>();
    }

    public void setField(TextField field, String pattern) {
        this.textFields.add(field);
        this.patterns.add(pattern);
    }

    public void disableButtons(boolean bool) {
        for (Button button : buttons) {
            button.setDisable(bool);
        }
    }

    public void attachButtons(Button... buttons) {
        this.buttons = buttons;
    }

    public void validate() {
        boolean bool = false;

        for (int i = 0; i < textFields.size(); i++) {
            bool = !textFields.get(i).getText().matches(patterns.get(i));

            if (bool) {
                i = textFields.size();
            }
        }

        if (buttons != null)
            disableButtons(bool);

    }
}
