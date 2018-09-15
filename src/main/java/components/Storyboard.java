package components;

import _interface.CanContain;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class Storyboard extends AnchorPane {
    @FXML private Label storyBoardTitle;
    @FXML private VBox storyContainer;

    private StringProperty titleText;

    public Storyboard() {
        this("Untitled");
    }

    public Storyboard(String title) {
        loadFxml();
        setTitle(title);
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/storyboard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public StringProperty titleTextProperty() {
        if (titleText == null) {
            titleText = new SimpleStringProperty();
        }
        return titleText;
    }

    public void setTitle(String title) {
        titleTextProperty().setValue(title);
    }

    public String getTitle() {
        return titleTextProperty().getValue();
    }

    public void addStory(Story story) {
        storyContainer.setSpacing(5);
        storyContainer.getChildren().add(story);
    }

    public void deleteStory(Story story) {
        storyContainer.getChildren().remove(story);
    }

    public void clear() {
        storyContainer.getChildren().clear();
    }


}
