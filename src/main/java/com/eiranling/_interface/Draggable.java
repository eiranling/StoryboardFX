package com.eiranling._interface;

import javafx.beans.property.BooleanProperty;

public interface Draggable {
    BooleanProperty draggableProperty();
    boolean isDraggable();
    void setDraggable(boolean draggable);
}
