import _enum.BadgeType;
import components.Badge;
import components.Story;
import components.Storyboard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import widgets.Board;

public class App extends Application {
    public void start(Stage primaryStage) throws Exception {
        primaryStage = new Stage();
        AnchorPane anchorPane = new AnchorPane();

        Story story = new Story();
        Badge success = new Badge();
        success.setBadgeTypeObject(BadgeType.BADGE_SUCCESS);

        story.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                success.setBadgeTypeObject(BadgeType.BADGE_FAILURE);
            }
        });

        story.addBadge(success);

        anchorPane.getChildren().add(story);

        Scene root = new Scene(anchorPane);
        primaryStage.setScene(root);
        primaryStage.show();
    }
}
