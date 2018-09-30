package com.eiranling.utils;

import com.eiranling._enum.ButtonStyle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class serves as a utility class to replace given nodes
 * with another node.
 * @author eiran
 */
public class NodeReplacer {
    /**
     * Replaces the old node of parent with new node
     * @param parent Pane object containing oldNode
     * @param oldNode Node to replace with new node
     * @param newNode Node to replace old node
     */
    public static void replaceNode(Pane parent, Node oldNode, Node newNode) {
        parent.getChildren().replaceAll(node -> {
            if (node.equals(oldNode)) {
                return newNode;
            }
            return node;
        });
    }

    /**
     * Helper function to remove any old button styles on the button and apply only the new one
     * @param button button to apply button style to
     * @param buttonStyle button style to apply
     */
    public static void changeButtonType(Button button, ButtonStyle buttonStyle) {
        Arrays.asList(ButtonStyle.values()).forEach(c -> {
            button.getStyleClass().remove(c.getStyleClass());
        });
        button.getStyleClass().add(buttonStyle.getStyleClass());
    }
}
