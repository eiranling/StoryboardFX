package components;

import _enum.BadgeType;
import _interface.CanContain;
import _interface.CanConvertControls;
import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import utils.NodeReplacer;
import utils.TextFieldToLabelConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class Storyboard extends AnchorPane implements CanConvertControls {
    @FXML private Control storyBoardTitle;
    @FXML private VBox storyContainer;

    private StringProperty titleText;
    private BooleanProperty userEditable;

    public Storyboard() {
        this("Untitled");
    }

    public Storyboard(String title) {
        loadFxml();
        setTitle(title);
        setOnMouseClicked(evt -> {
            if (evt.getClickCount() == 2 && evt.getButton().equals(MouseButton.PRIMARY) && isUserEditable()) {
                editTitle();
            }
        });
        setOnDragOver(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });
        setOnDragEntered(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasString()) {
                this.setStyle("-fx-border-width: 5px; -fx-border-color: BLACK");
            }
            event.consume();
        });
        setOnDragExited(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasString()) {
                this.setStyle("");
            }
            event.consume();
        });
        setOnDragDropped(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasString()) {
                Story temp = new Story();
                for (BadgeType badgeType : (Collection<BadgeType>) event.getDragboard().getContent(new DataFormat("badge"))) {
                    temp.addBadge(badgeType);
                }
                temp.setTitle(event.getDragboard().getContent(new DataFormat("story")).toString());
                getChildren().add(temp);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
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

    public BooleanProperty userEditableProperty() {
        if (userEditable == null) {
            userEditable = new SimpleBooleanProperty(true);
        }
        return userEditable;
    }

    public void setUserEditable(boolean userEditable) {
        userEditableProperty().setValue(userEditable);
    }

    public boolean isUserEditable() {
        return userEditableProperty().getValue();
    }

    @FXML
    public void addStory() {
        this.addStory(new Story());
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

    public void editTitle() {
        TextField temp = TextFieldToLabelConverter.generateTextField(this);
        temp.setText(titleTextProperty().getValue());
        NodeReplacer.replaceNode(this, storyBoardTitle, temp);
        storyBoardTitle = temp;
        storyBoardTitle.requestFocus();
    }


    @Override
    public void finishEdit(String finalText) {
        Label label = TextFieldToLabelConverter.generateLabel(finalText);
        NodeReplacer.replaceNode(this, storyBoardTitle, label);
        storyBoardTitle = label;
        setTitle(label.getText());
    }
}