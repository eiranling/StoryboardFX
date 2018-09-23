package com.eiranling._enum;

import javafx.scene.input.DataFormat;

/**
 * Predefined DataFormat objects for dragging and dropping Stories between Storyboards
 */
public enum DataFormats {
    STORY(new DataFormat("story")),
    BADGE(new DataFormat("badge")),
    USER_DATA(new DataFormat("user_data"));

    private DataFormat dataFormat;

    DataFormats(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }
}
