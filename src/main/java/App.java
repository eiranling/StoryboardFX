import _enum.BadgeType;
import components.Story;
import components.Storyboard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();

        Storyboard storyboard = new Storyboard();

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Story temp = new Story();
                temp.setTitle("Story ");
                storyboard.addStory(temp);
            }
        });

        anchorPane.getChildren().add(storyboard);

        Scene root = new Scene(anchorPane);
        stage.setScene(root);
        stage.show();
    }
}
