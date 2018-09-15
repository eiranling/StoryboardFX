import _enum.BadgeType;
import components.Story;
import components.Storyboard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();

        Story story = new Story();

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.A)) {
                story.addBadge(BadgeType.BADGE_FAILURE);
            }
        });

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.M)) {
                story.removeBadge(BadgeType.BADGE_FAILURE);
            }
        });

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.D)) {
                story.addBadge("Blood Test", "badge-failure");
            }
        });

        Storyboard storyboard = new Storyboard();
        storyboard.addStory(story);

        anchorPane.getChildren().add(storyboard);

        Scene root = new Scene(anchorPane);
        stage.setScene(root);
        stage.show();
    }
}
