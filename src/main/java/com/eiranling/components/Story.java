package com.eiranling.components;

import com.eiranling._enum.BadgeType;
import com.eiranling._interface.CanConvertControls;
import com.eiranling._interface.Draggable;
import com.eiranling._interface.UserEditable;
import com.eiranling.utils.ComponentLoader;
import com.eiranling.utils.NodeReplacer;
import com.eiranling.utils.TextFieldToLabelConverter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

import java.util.Collection;
import java.util.HashSet;

import static com.eiranling._enum.DataFormats.*;

/**
 * Component for holding user data to move around the Storyboards
 * @see Storyboard
 * @param <T> Object type to store in the Story
 * @author eiran
 */
public class Story<T extends Object> extends VBox implements CanConvertControls, Draggable, UserEditable {
    /** Control item for displaying the title of the story */
    @FXML private Control title;
    /** Container for holding the badges associated with the Story */
    @FXML private HBox badgeContainer;
    @FXML private Button remove;
    @FXML private HBox customNodeContainer;

    private StringProperty titleText;
    private BooleanProperty userEditable;
    private BooleanProperty draggable;
    private ObjectProperty<T> containedData;
    private ListProperty<Node> customControls;

    /**
     * Creates a Story object with the title "Untitled"
     */
    public Story() {
        this("Untitled");
    }

    /**
     * Creates a Story object with the specified title
     * @param title title of the Story object
     */
    public Story(String title) {
        loadFxml();
        bind();
        setTitle(title);
    }

    /**
     * Adds event handlers to the story.
     */
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

        setOnMouseEntered(event -> remove.setVisible(true));
        setOnMouseExited(event -> remove.setVisible(false));

        setOnDragDetected(event -> {
            // Sets drag and drop
            if (isDraggable()) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);

                db.setDragView(this.snapshot(null, null));

                final Collection<Badge> badges = new HashSet<>();
                badgeContainer.getChildren().forEach(b -> badges.add(new Badge(((Badge) b).getText(), ((Badge) b).styleClassProperty().get())));

                ClipboardContent content = new ClipboardContent();
                content.put(STORY.getDataFormat(), getTitle());
                content.put(BADGE.getDataFormat(), badges);
                if (getUserData() != null) {
                    content.put(USER_DATA.getDataFormat(), getUserData());
                }

                db.setContent(content);
            }
            event.consume();
        });

        customControlsProperty().addListener((ListChangeListener<Node>) c -> {
            customNodeContainer.getChildren().removeAll(c.getRemoved());
            customNodeContainer.getChildren().addAll(c.getAddedSubList());
        });
    }

    /**
     * Sets the title of the Story instance
     * @param title title of the Story
     */
    public void setTitle(String title) {
        titleTextProperty().setValue(title);
    }

    /**
     * @return The title of the Story
     */
    public String getTitle() {
        return titleTextProperty().getValue();
    }

    /**
     * @return the StringProperty of the title
     */
    public StringProperty titleTextProperty() {
        if (titleText == null) {
            titleText = new SimpleStringProperty("Untitled");
        }
        return titleText;
    }

    /**
     * Adds a pre-defined badge to the Story
     * @param badgeType pre-defined badge type
     */
    public void addBadge(BadgeType badgeType) {
        this.addBadge(badgeType.getBadgeText(), badgeType.getStyleClass());
    }

    /**
     * Adds a custom badge type to the Story
     * @param badgeText Text to show on the badge to add
     * @param badgeStyleClass Style class to display with the badge
     */
    public void addBadge(String badgeText, String badgeStyleClass) {
        Badge badge = new Badge();
        badge.setBadgeType(badgeText, badgeStyleClass);
        if (!badgeContainer.getChildren().contains(badge)) {
            badgeContainer.getChildren().add(badge);
        }
    }

    /**
     * Removes the specified badge from the Story if it exists
     * @param badge badge to remove from the Story
     */
    public void removeBadge(Badge badge) {
        badgeContainer.getChildren().remove(badge);
    }

    /**
     * Used to change the title from a Label to a TextField.
     * By default, this is called on double click, and the title is set with the Enter key being pressed
     * while the TextField has focus
     */
    public void editTitle() {
        TextField temp = TextFieldToLabelConverter.generateTextField(this);
        temp.setText(titleTextProperty().getValue());
        NodeReplacer.replaceNode((Pane) title.getParent(), title, temp);
        title = temp;
        title.requestFocus();
    }

    /**
     * Converts the TextField into a Label and sets the title to be the text contained in the
     * TextField
     */
    @Override
    public void finishEdit() {
        Label label = TextFieldToLabelConverter.generateLabel(((TextField) title).getText());
        NodeReplacer.replaceNode((Pane) title.getParent(), title, label);
        title = label;
        setTitle(label.getText());
    }


    private void loadFxml() {
        ComponentLoader.loadFXML(getClass().getResource("/FXML/story.fxml"), this);
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

    public ListProperty<Node> customControlsProperty() {
        if (customControls == null) {
            customControls = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return customControls;
    }

    public ObservableList<Node> getCustomControls() {
        return customControlsProperty().get();
    }

    public void setCustomControls(ObservableList<Node> nodes) {
        customControlsProperty().set(nodes);
    }
}
