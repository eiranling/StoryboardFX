package com.eiranling._interface;

import javafx.beans.property.BooleanProperty;

public interface UserEditable {
    BooleanProperty userEditableProperty();
    boolean isUserEditable();
    void setUserEditable(boolean editable);
}
