package screens;

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

        // === Game loop ===
        GameManager gameManager = new GameManager();
        gameManager.setGameLoop(scene, gc, levelPath);

        // === Nút Pause ===
        Button btnPause = new Button("⏸ Pause");
        btnPause.setFocusTraversable(false);
        btnPause.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(0,0,0,0.5); -fx-text-fill: white;");
        btnPause.setOnAction(e -> {
            if (btnPause.getText().equals("⏸ Pause")) {
                gameManager.pauseGame();
                btnPause.setText("▶ Resume");
            } else {
                gameManager.resumeGame();
                btnPause.setText("⏸ Pause");
            }
        });

        root.getChildren().add(btnPause);
        StackPane.setAlignment(btnPause, Pos.TOP_RIGHT);
        StackPane.setMargin(btnPause, new Insets(10));

        return scene;
    }
}
