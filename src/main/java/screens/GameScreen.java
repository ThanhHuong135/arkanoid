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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        levelOne.getPaddle().setGoLeft(true);
                        break;
                    case RIGHT:
                        levelOne.getPaddle().setGoRight(true);
                        break;
//                    case SPACE:
//                        gamestarted = true;
//                        break;
//                    case ESCAPE:
//                        gamestarted = false;
//                        break;
                    default:
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        levelOne.getPaddle().setGoLeft(false);
                        break;
                    case RIGHT:
                        levelOne.getPaddle().setGoRight(false);
                        break;
                    default:
                        break;
                }
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
