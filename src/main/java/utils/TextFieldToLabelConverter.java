package utils;

import _interface.CanConvertControls;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

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
