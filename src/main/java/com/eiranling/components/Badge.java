package com.eiranling.components;

import com.eiranling._enum.BadgeType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

/**
 * Badge component used in Story
 * @see Story
 */
public class Badge extends Label {

    private StringProperty styleClass;

    /**
     * Default constructor to create a new badge with a black background and Default text
     */
    public Badge() {
        this(BadgeType.BADGE_DEFAULT);
    }

    /**
     * Constructor to create a new badge based on existing badge type
     * @param badgeType BadgeType containing the text and the styleclass
     */
    public Badge(BadgeType badgeType) {
        this(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    /**
     * Constructor to create a new badge with custom text and a custom styleClass
     * @param badgeText Text to put into the badge
     * @param styleClass Styleclass of the badge
     */
    public Badge(String badgeText, String styleClass) {
        loadFxml();
        bind();
        setBadgeType(badgeText, styleClass);
    }

    /**
     * Method to bind listeners to properties
     */
    private void bind() {
        styleClassProperty().addListener((observable, oldVal, newVal) -> {
            getStyleClass().remove(oldVal);
            getStyleClass().add(newVal);
        });
    }

    /**
     * Returns the styleclass property of this badge
     * @return The styleclass property of this badge
     */
    public StringProperty styleClassProperty() {
        if (styleClass == null) {
            styleClass = new SimpleStringProperty(BadgeType.BADGE_DEFAULT.getStyleClass());
        }
        return styleClass;
    }

    /**
     * Sets the badge type to be a predefined badge
     * @param badgeType pre-defined badge type
     */
    public void setBadgeType(BadgeType badgeType) {
        setBadgeType(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    /**
     * Sets the badge type to be a custom badge with badge text and custom styleclasses
     * @param text Text of the title
     * @param styleClass
     */
    public void setBadgeType(String text, String styleClass) {
        setStyle(styleClass);
        setText(text);
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
