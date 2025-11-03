package screens;
import Ranking.HighScoreManager;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import manager.GameManager;

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

//        finalScore = gameManager.getScore();
//        MainMenuScreen.highScoreManager.addScore(finalScore);
//        MainMenuScreen.highScoreManager.writeToFile();
        return scene;
    }
}
