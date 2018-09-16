package components;

import _enum.BadgeType;
import _interface.CanConvertControls;
import _interface.Component;
import _interface.UserEditable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import utils.NodeReplacer;
import utils.TextFieldToLabelConverter;

import java.io.IOException;
import java.util.Collection;

import static _enum.DataFormats.BADGE;
import static _enum.DataFormats.STORY;

public class Storyboard extends AnchorPane implements CanConvertControls, UserEditable, Component {
    @FXML private Control storyboardTitle;
    @FXML private VBox storyContainer;

    private StringProperty titleText;
    private BooleanProperty userEditable;

    public Storyboard() {
        this("Untitled");
    }

    public Storyboard(String title) {
        loadFxml();
        bind();
        setTitle(title);
    }

    private void bind() {
        titleTextProperty().addListener((observable, oldValue, newValue) -> {
            if (storyboardTitle instanceof Label) ((Label) storyboardTitle).setText(newValue); // Only updates title if the component is a Label
        });

        setOnMouseClicked(evt -> {
            if (evt.getClickCount() == 2 && evt.getButton().equals(MouseButton.PRIMARY) && isUserEditable()) {
                editTitle();
            }
        });

        setOnDragOver(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasContent(STORY.getDataFormat())) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        setOnDragEntered(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasContent(STORY.getDataFormat())) {
                this.setStyle("-fx-border-width: 5px; -fx-border-color: BLACK");
            }
        });

        setOnDragExited(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasContent(STORY.getDataFormat())) {
                this.setStyle("");
            }
        });

        setOnDragDropped(event -> {
            if (!getChildren().contains(event.getGestureSource()) && event.getDragboard().hasContent(STORY.getDataFormat())) {
                Story temp = new Story(event.getDragboard().getContent(STORY.getDataFormat()).toString());
                for (BadgeType badgeType : (Collection<BadgeType>) event.getDragboard().getContent(BADGE.getDataFormat())) {
                    temp.addBadge(badgeType);
                }
                addStory(temp);
                ((Story) event.getGestureSource()).removeFromParent();
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });
    }

    @Override
    public void loadFxml() {
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
        AnchorPane.setLeftAnchor(temp, 0.0);
        AnchorPane.setRightAnchor(temp, 30.0);
        AnchorPane.setTopAnchor(temp, 0.0);
        NodeReplacer.replaceNode(this, storyboardTitle, temp);
        storyboardTitle = temp;
        storyboardTitle.requestFocus();
    }


    @Override
    public void finishEdit(String finalText) {
        Label label = TextFieldToLabelConverter.generateLabel(finalText);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 30.0);
        AnchorPane.setTopAnchor(label, 0.0);
        NodeReplacer.replaceNode(this, storyboardTitle, label);
        storyboardTitle = label;
        setTitle(label.getText());
    }
}
