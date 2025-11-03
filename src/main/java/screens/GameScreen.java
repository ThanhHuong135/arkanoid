package screens;
import Ranking.HighScoreManager;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import manager.GameManager;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import manager.SoundManager;

public class GameScreen {
    private static int finalScore;
    public static Scene createScene(Stage stage, String levelPath) {
        // --- Background ImageView ---
        Image bgImage = new Image(GameScreen.class.getResourceAsStream("/assets/images/background-game-screen.png"));
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(700);
        bgView.setFitHeight(650);
        bgView.setPreserveRatio(false);
        bgView.setSmooth(false);

        // --- Canvas để vẽ các object ---
        Canvas canvas = new Canvas(700, 650);
        // canvas mặc định trong suốt → background nhìn thấy

        // --- StackPane chứa background và canvas ---
        StackPane root = new StackPane(bgView, canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root, 700, 650);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );

        // --- Game loop ---
        GameManager gameManager = new GameManager();
        gameManager.setGameLoop(scene, gc, levelPath);

        // --- Pause / Resume button ---
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
        StackPane.setMargin(btnPause, new Insets(10)); // cách viền 10px

//        finalScore = gameManager.getScore();
//        MainMenuScreen.highScoreManager.addScore(finalScore);
//        MainMenuScreen.highScoreManager.writeToFile();
        return scene;
    }
}
