package com.eiranling.components;

import com.eiranling._enum.BadgeType;
import com.eiranling._interface.*;
import com.eiranling.utils.NodeReplacer;
import com.eiranling.utils.TextFieldToLabelConverter;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import static com.eiranling._enum.DataFormats.BADGE;
import static com.eiranling._enum.DataFormats.STORY;

public class Story<T extends Object> extends VBox implements CanConvertControls, Draggable, UserEditable, Component, CanContainData<T> {
    @FXML private Control title;
    @FXML private HBox badgeContainer;

    private StringProperty titleText;
    private BooleanProperty userEditable;
    private BooleanProperty draggable;
    private ObjectProperty<T> containedData;

    public Story() {
        this("Untitled");
    }

    public Story(String title) {
        loadFxml();
        bind();
        setTitle(title);


    }

    private void bind() {
        titleTextProperty().addListener(((observable, oldValue, newValue) -> {
            if (title instanceof Label) ((Label) title).setText(newValue);
        }));

        setOnMouseClicked(event -> {
            // Sets double click to change title
            if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY) && isUserEditable()) {
                editTitle();
            }
            event.consume();
        });

        setOnDragDetected(event -> {
            // Sets drag and drop
            if (isDraggable()) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                db.setDragView(this.snapshot(null, null));

                final Collection<BadgeType> badges = new HashSet<>();
                badgeContainer.getChildren().forEach(b -> badges.add(((Badge) b).getBadgeType()));

                ClipboardContent content = new ClipboardContent();
                content.put(STORY.getDataFormat(), getTitle());
                content.put(BADGE.getDataFormat(), badges);


                db.setContent(content);
            }
            event.consume();
        });

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

    public void editTitle() {
        TextField temp = TextFieldToLabelConverter.generateTextField(this);
        temp.setText(titleTextProperty().getValue());
        NodeReplacer.replaceNode(this, title, temp);
        title = temp;
        title.requestFocus();
    }

    @Override
    public void finishEdit(String finalText) {
        Label label = TextFieldToLabelConverter.generateLabel(finalText);
        NodeReplacer.replaceNode(this, title, label);
        title = label;
        setTitle(label.getText());
    }


    @Override
    public void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/story.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void removeFromParent() {
        ((Pane) getParent()).getChildren().remove(this);
    }

    @Override
    public BooleanProperty draggableProperty() {
        if (draggable == null) {
            draggable = new SimpleBooleanProperty(true);
        }
        return draggable;
    }

    @Override
    public boolean isDraggable() {
        return draggableProperty().getValue();
    }

    @Override
    public void setDraggable(boolean draggable) {
        draggableProperty().setValue(draggable);
    }

    @Override
    public BooleanProperty userEditableProperty() {
        if (userEditable == null) {
            userEditable = new SimpleBooleanProperty(true);
        }
        return userEditable;
    }

    @Override
    public boolean isUserEditable() {
        return userEditableProperty().getValue();
    }

    @Override
    public void setUserEditable(boolean userEditable) {
        userEditableProperty().setValue(userEditable);
    }

    public ObjectProperty<T> containedDataProperty() {
        if (containedData == null) {
            containedData = new SimpleObjectProperty<>(null);
        }
        return containedData;
    }

    public T getContainedData() {
        return containedDataProperty().getValue();
    }

    @Override
    public void setContainedData(T data) {
        containedDataProperty().setValue(data);
    }
}