package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import manager.GameManager;
import manager.InputManager;

import java.net.URL;

public class GameScreen {
    private static int finalScore;

    public static Scene createScene(Stage stage, String levelPath) {

        // === VIDEO BACKGROUND ===
        URL videoPath = GameScreen.class.getResource("/assets/images/background.mp4");
        Media media = new Media(videoPath.toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(false);

        // === Canvas ===
        Canvas canvas = new Canvas(700, 650);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // === Quản lý game & input ===
        GameManager gameManager = new GameManager();
        InputManager inputManager = new InputManager();

        // === Overlay EndGame + Pause ===
        VBox endGameMenu = EndGameScreen.createMenu(stage, gameManager, inputManager, levelPath);
        VBox pauseMenu = PauseGameScreen.createMenu(stage, gameManager, inputManager, levelPath);

        // === Layout chính ===
        StackPane root = new StackPane(mediaView, canvas, pauseMenu, endGameMenu);
        StackPane.setAlignment(pauseMenu, Pos.CENTER);
        StackPane.setAlignment(endGameMenu, Pos.CENTER);

        Scene scene = new Scene(root, 700, 650);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );

        // === Ràng buộc kích thước ===
        mediaView.fitWidthProperty().bind(scene.widthProperty());
        mediaView.fitHeightProperty().bind(scene.heightProperty());
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        // === Game loop ===
        gameManager.setGameLoop(scene, gc, levelPath, endGameMenu, pauseMenu);

        return scene;
    }
}
