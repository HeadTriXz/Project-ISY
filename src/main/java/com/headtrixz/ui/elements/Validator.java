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

    /**
     *  The constructor of the Visitor class.
     *  This wil be initialized the variables text-fields and patterns as an arraylist.
     */
    public Validator() {
        textFields = new ArrayList<>();
        patterns = new ArrayList<>();
    }

    /**
     * Sets the fields and patterns in a var
     *
     * @param field the field what must be saved.
     * @param pattern the field what must be saved.
     */
    public void setField(TextField field, String pattern) {
        this.textFields.add(field);
        this.patterns.add(pattern);
    }

    /**
     *  This disables buttons on the frontend
     */
    public void disableButtons(boolean bool) {
        for (Button button : buttons) {
            button.setDisable(bool);
        }
    }

    /**
     *  This attached the buttons so it can be used at validate
     */
    public void attachButtons(Button... buttons) {
        this.buttons = buttons;
    }

    /**
     *  This method checks all text-fields for each pattern is given.
     *  It also disable buttons if they are given.
     */
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
