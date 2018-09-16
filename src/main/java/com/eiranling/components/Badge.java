package com.eiranling.components;

import com.eiranling._enum.BadgeType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

public class Badge extends Label {

    private StringProperty styleClass;

    public Badge() {
        this(BadgeType.BADGE_DEFAULT);
    }

    public Badge(BadgeType badgeType) {
        this(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    public Badge(String badgeText, String styleClass) {
        loadFxml();
        bind();
        setBadgeType(badgeText, styleClass);
    }

    private void bind() {
        styleClassProperty().addListener((observable, oldVal, newVal) -> {
            getStyleClass().remove(oldVal);
            getStyleClass().add(newVal);
        });
    }

    public StringProperty styleClassProperty() {
        if (styleClass == null) {
            styleClass = new SimpleStringProperty(BadgeType.BADGE_DEFAULT.getStyleClass());
        }
        return styleClass;
    }

    public void setBadgeType(BadgeType badgeType) {
        styleClassProperty().setValue(badgeType.getStyleClass());
        textProperty().setValue(badgeType.getBadgeText());
    }

    public void setBadgeType(String text, String styleClass) {
        styleClassProperty().setValue(styleClass);
        textProperty().set(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(styleClassProperty().getValue(), badge.styleClassProperty().getValue()) &&
                Objects.equals(textProperty().getValue(), badge.textProperty().getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(styleClassProperty());
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/badge.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public BadgeType getBadgeType() {
        return BadgeType.BADGE_SUCCESS;
    }
}
