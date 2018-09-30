package com.eiranling._enum;

public enum ButtonStyle {
    CONFIRM("confirm-button"),
    REMOVE("remove-button"),
    EDIT("edit-button");

    private String styleClass;

    ButtonStyle(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

}
