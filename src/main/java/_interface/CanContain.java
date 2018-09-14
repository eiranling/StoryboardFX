package _interface;

import javafx.scene.Node;

public interface CanContain {
    boolean addNode(Node node);
    boolean removeNode(Node node);
}
