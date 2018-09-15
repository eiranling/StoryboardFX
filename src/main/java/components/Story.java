package components;

import _enum.BadgeType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Story extends VBox {
    @FXML private Label title;
    @FXML private HBox badgeContainer;

    private StringProperty titleText;

    public Story() {
        loadFxml();
        bind();
    }

    private void bind() {
        titleTextProperty().addListener(((observable, oldValue, newValue) -> title.setText(newValue)));
    }

    public void setTitle(String title) {
        titleTextProperty().setValue(title);
    }

    public String getTitle() {
        return titleTextProperty().getValue();
    }

    public StringProperty titleTextProperty() {
        if (titleText == null) {
            titleText = new SimpleStringProperty("Untitled");
        }
        return titleText;
    }

    public void addBadge(BadgeType badgeType) {
        this.addBadge(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    public void addBadge(String badgeText, String badgeStyleClass) {
        Badge badge = new Badge();
        badge.setBadgeType(badgeText, badgeStyleClass);
        if (!badgeContainer.getChildren().contains(badge)) {
            badgeContainer.getChildren().add(badge);
        }
    }

    public void removeBadge(BadgeType badgeType) {
        badgeContainer.getChildren().remove(new Badge(badgeType));
    }


    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/story.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
