package utils;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class NodeReplacer {
    public static void replaceNode(Pane parent, Node oldNode, Node newNode) {
        parent.getChildren().replaceAll(node -> {
            if (node.equals(oldNode)) {
                return newNode;
            }
            return node;
        });
    }
}
