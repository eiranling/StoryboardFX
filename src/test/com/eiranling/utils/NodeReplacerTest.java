package com.eiranling.utils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeReplacerTest extends Application {

    @Before
    public void setUp() throws Exception {
        start(new Stage());
    }

    @Test
    public void testNodeReplacedInPane() {
        Platform.runLater(() -> {
            HBox parent = new HBox();
            Label toReplace = new Label();
            parent.getChildren().add(toReplace);

            TextField textfield = new TextField();
            NodeReplacer.replaceNode(parent, toReplace, textfield);
            Assert.assertTrue(parent.getChildren().contains(textfield));
            Assert.assertFalse(parent.getChildren().contains(toReplace));
        });
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
