package com.eiranling.utils;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

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
}
