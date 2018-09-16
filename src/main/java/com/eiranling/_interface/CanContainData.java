package com.eiranling._interface;

import javafx.beans.property.ObjectProperty;

public interface CanContainData<T> {
    ObjectProperty<T> containedDataProperty();
    T getContainedData();
    void setContainedData(T data);
}
