package _enum;

import javafx.scene.input.DataFormat;

public enum DataFormats {
    STORY(new DataFormat("story")),
    BADGE(new DataFormat("badge"));

    private DataFormat dataFormat;

    DataFormats(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }
}
