package screens;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import level.LevelOne;
import object.Controller;

public class GameScreen {

    public static Scene createScene(Stage stage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("root"); // áp CSS

        Canvas canvas = new Canvas(700, 650);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root, 700, 650);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );

        Controller controller = new Controller();
        controller.setGameLoop(scene, gc);

        return scene;
    }
}
