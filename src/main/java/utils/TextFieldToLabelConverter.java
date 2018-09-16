package utils;

import _interface.CanConvertControls;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TextFieldToLabelConverter {
    public static TextField generateTextField(CanConvertControls converter) {
        TextField editTextField = new TextField();
        editTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                converter.finishEdit(editTextField.getText());
            }
        });
        return editTextField;
    }

    public static Label generateLabel(String text) {
        return new Label(text);
    }

}
