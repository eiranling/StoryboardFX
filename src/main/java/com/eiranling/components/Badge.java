package com.eiranling.components;

import com.eiranling._enum.BadgeType;
import com.eiranling.utils.ComponentLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

/**
 * Custom widget for adding tags to a story.
 * @author eiran
 */
public class Badge extends Label {

    private StringProperty styleClass;

    /**
     * Empty constructor for creating a default badge
     */
    public Badge() {
        this(BadgeType.BADGE_DEFAULT);
    }

    /**
     * Constructor to create a badge with a specified pre-defined BadgeType
     * @param badgeType pre-defined BadgeType object
     */
    public Badge(BadgeType badgeType) {
        this(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    /**
     * Constructor to create a custom Badge with different text and style class
     * @param badgeText Text to appear on the badge
     * @param styleClass ButtonStyle class to apply to the Badge
     */
    public Badge(String badgeText, String styleClass) {
        loadFxml();
        bind();
        setBadgeType(badgeText, styleClass);
    }

    /**
     * Binds listeners to the following properties:
     * - styleClass: ensures the last styleClass is cleared before adding in the new one
     */
    private void bind() {
        styleClassProperty().addListener((observable, oldVal, newVal) -> {
            getStyleClass().remove(oldVal);
            getStyleClass().add(newVal);
        });
    }

    /**
     * Returns the style class property held by the Badge instance
     * @return the style class property
     */
    public StringProperty styleClassProperty() {
        if (styleClass == null) {
            styleClass = new SimpleStringProperty(BadgeType.BADGE_DEFAULT.getStyleClass());
        }
        return styleClass;
    }

    /**
     * Sets the Badge to have the text and style class of a pre-defined BadgeType
     * @param badgeType pre-defined BadgeType to assume to style and text of
     */
    public void setBadgeType(BadgeType badgeType) {
        setBadgeType(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    /**
     * Sets the Badge to have a custom text and style class
     * @param text text to appear on the Badge instance
     * @param styleClass style class to style the Badge instance with
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

    /**
     * Method to load the relevant .fxml file for this component
     */
    private void loadFxml() {
        ComponentLoader.loadFXML(getClass().getResource("/FXML/badge.fxml"), this);
    }
}
