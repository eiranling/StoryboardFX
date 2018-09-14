package components;

import _enum.BadgeType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class Badge extends Label {

    private ObjectProperty<BadgeType> badgeTypeObject;

    public Badge() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/badge.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        bind();

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void bind() {
        badgeTypeObjectProperty().addListener((observable, newVal, oldVal) -> {
            System.out.println(newVal);
            System.out.println(oldVal);
            if (newVal.equals(oldVal)) {
                return;
            }
            getStyleClass().remove(oldVal.getStyleClass());
            getStyleClass().add(newVal.getStyleClass());
            setText(newVal.getBadgeText());
        });
    }

    public ObjectProperty<BadgeType> badgeTypeObjectProperty() {
        if (badgeTypeObject == null) {
            badgeTypeObject = new SimpleObjectProperty<>(BadgeType.BADGE_SUCCESS);
        }
        return badgeTypeObject;
    }

    public void setBadgeTypeObject(BadgeType badgeType) {
        badgeTypeObjectProperty().setValue(badgeType);
    }
}
