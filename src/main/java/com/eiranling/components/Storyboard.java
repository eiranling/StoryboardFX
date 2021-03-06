package com.eiranling.components;

import com.eiranling._enum.BadgeType;
import com.eiranling._interface.CanConvertControls;
import com.eiranling._interface.UserEditable;
import com.eiranling.utils.ComponentLoader;
import com.eiranling.utils.ControlFactory;
import com.eiranling.utils.NodeReplacer;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Collection;

import static com.eiranling._enum.DataFormats.*;

public class Storyboard extends AnchorPane implements CanConvertControls, UserEditable {
    @FXML private Control storyboardTitle;
    @FXML private VBox storyContainer;

    private StringProperty titleText;
    private BooleanProperty userEditable;
    private ObjectProperty<Callback<Storyboard, Story>> storyFactory;

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
                if (event.getDragboard().hasContent(USER_DATA.getDataFormat())) {
                    temp.setUserData(event.getDragboard().getContent(USER_DATA.getDataFormat()));
                }
                addStory(temp);
                ((Story) event.getGestureSource()).removeFromParent();
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });
    }

    private void loadFxml() {
        ComponentLoader.loadFXML(getClass().getResource("/FXML/storyboard.fxml"), this);
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
        this.addStory(getStoryFactory().call(this));
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
        TextField temp = ControlFactory.generateTextField(this);
        temp.setText(titleTextProperty().getValue());
        AnchorPane.setLeftAnchor(temp, 0.0);
        AnchorPane.setRightAnchor(temp, 30.0);
        AnchorPane.setTopAnchor(temp, 0.0);
        NodeReplacer.replaceNode(this, storyboardTitle, temp);
        storyboardTitle = temp;
        storyboardTitle.requestFocus();
    }


    @Override
    public void finishEdit() {
        Label label = ControlFactory.generateLabel(((TextField) storyboardTitle).getText());
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 30.0);
        AnchorPane.setTopAnchor(label, 0.0);
        NodeReplacer.replaceNode(this, storyboardTitle, label);
        storyboardTitle = label;
        setTitle(label.getText());
    }

    public ObjectProperty<Callback<Storyboard, Story>> storyFactoryProperty() {
        if (storyFactory == null) {
            storyFactory = new SimpleObjectProperty<>(param -> new Story("Untitled"));
        }
        return storyFactory;
    }

    public Callback<Storyboard, Story> getStoryFactory() {
        return storyFactoryProperty().get();
    }

    public void setStoryFactory(Callback<Storyboard, Story> factory) {
        storyFactoryProperty().set(factory);
    }
}
