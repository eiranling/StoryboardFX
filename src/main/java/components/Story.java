package components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Story extends VBox {
    @FXML private Label title;
    @FXML private HBox badgeContainer;

    public Story() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/story.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addBadge(Badge badge) {
        badgeContainer.getChildren().add(badge);
    }

    public void removeBadge(Badge badge) {
        badgeContainer.getChildren().remove(badge);
    }
}
