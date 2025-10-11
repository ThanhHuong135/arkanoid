package screens;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import level.LevelOne;

public class GameScreen {

    public static Scene createScene(Stage stage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("root"); // áp CSS

        Canvas canvas = new Canvas(500, 650);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root, 500, 650);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );

        LevelOne levelOne = new LevelOne(canvas.getWidth(), canvas.getHeight());
        levelOne.init();

        // --- Xử lý di chuyển paddle bằng bàn phím ---
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> levelOne.getPaddle().moveLeft();
                case RIGHT -> levelOne.getPaddle().moveRight(canvas.getWidth());
            }
        });

        // Animation loop để render LevelOne
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                levelOne.update();  // hiện tại chưa logic
                levelOne.render(gc);
            }
        }.start();

        return scene;
    }
}
