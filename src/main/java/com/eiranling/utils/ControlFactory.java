package com.eiranling.utils;

import com.eiranling._interface.CanConvertControls;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Factory to generate builtins that inherit the JavaFX Control class
 */
public class ControlFactory {
    /**
     * Generates a new textfield which calls the finishEdit method of the provided CanConvertControls instance
     * @param converter converter to call when the enter key is pressed
     * @return the new textfield for entry
     */
    public static TextField generateTextField(CanConvertControls converter) {
        TextField editTextField = new TextField();
        editTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                converter.finishEdit();
            }
        });
        return editTextField;
    }

    /**
     * Creates a label with the given text
     * @param text text to show in the label
     * @return the Label with the given text
     */
    public static Label generateLabel(String text) {
        return new Label(text);
    }

}
