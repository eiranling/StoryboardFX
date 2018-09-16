import _enum.BadgeType;
import components.Story;
import components.Storyboard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        Pane anchorPane = new HBox();

        Storyboard storyboard = new Storyboard();
        Storyboard storyboard1 = new Storyboard();

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Story temp = new Story();
                temp.setTitle("Story ");
                storyboard.addStory(temp);
            }
        });

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.A)) {
                storyboard.getChildren().forEach(c -> {
                    if (c instanceof VBox) {
                        ((VBox) c).getChildren().forEach(s -> ((Story) s).addBadge(BadgeType.BADGE_SUCCESS));
                    }
                });
            }
        });

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.S)) {
                storyboard.getChildren().forEach(c -> {
                    if (c instanceof VBox) {
                        ((VBox) c).getChildren().forEach(s -> ((Story) s).removeBadge(BadgeType.BADGE_SUCCESS));
                    }
                });
            }
        });

        anchorPane.getChildren().add(storyboard);
        anchorPane.getChildren().add(storyboard1);

        Scene root = new Scene(anchorPane);
        stage.setScene(root);
        stage.show();
    }
}
