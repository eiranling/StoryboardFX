package com.eiranling.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class ComponentLoader {
    public static void loadFXML(URL location, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setRoot(node);
        fxmlLoader.setController(node);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
