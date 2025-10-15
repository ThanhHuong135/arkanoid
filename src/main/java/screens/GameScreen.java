package screens;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import manager.GameManager;

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

        GameManager gameManager = new GameManager();
        gameManager.setGameLoop(scene, gc);

        return scene;
    }
}
