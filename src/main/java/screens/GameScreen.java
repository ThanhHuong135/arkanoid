package screens;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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

        // === Layout chính ===
        StackPane root = new StackPane(mediaView, canvas);
        Scene scene = new Scene(root, 700, 650);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );

        // === Ràng buộc kích thước ===
        mediaView.fitWidthProperty().bind(scene.widthProperty());
        mediaView.fitHeightProperty().bind(scene.heightProperty());
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // === Overlay EndGame ===
        StackPane endGameOverlay = EndGameScreen.createOverlay(stage, levelPath);
        GameManager gameManager = new GameManager();
        InputManager inputManager = new InputManager();

        // đảm bảo overlay phủ toàn bộ màn hình GameScreen
        endGameOverlay.setVisible(false);
        endGameOverlay.setStyle("-fx-background-color: rgba(0,0,0,0.6);");

        // thêm overlay sau cùng để nằm trên cùng
        root.getChildren().add(endGameOverlay);
        StackPane.setAlignment(endGameOverlay, Pos.CENTER);

        // === PauseGame ===
        VBox pauseMenu = PauseGameScreen.createMenu(stage, gameManager, inputManager, levelPath);
        root.getChildren().add(pauseMenu);
        StackPane.setAlignment(pauseMenu, Pos.CENTER);
        // === Game loop ===

        gameManager.setGameLoop(scene, gc, levelPath, endGameOverlay, pauseMenu);
        return scene;
    }
}
