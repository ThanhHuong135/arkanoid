package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import manager.GameManager;
import manager.InputManager;

public class EndGameScreen {

    public static VBox createMenu(Stage stage, GameManager gameManager, InputManager inputManager, String levelPath) {
        VBox overlay = new VBox(15);
        overlay.setAlignment(Pos.CENTER);
        overlay.setPadding(new Insets(20));
        overlay.setPrefWidth(200);   // rộng 400px
        overlay.setPrefHeight(250);
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setVisible(false); // ban đầu ẩn

        // === Tiêu đề GAME OVER ===
        Text title = new Text("GAME OVER");
        title.setFont(Font.font("Consolas", 70));
        title.setFill(Color.web("#00eaff"));
        title.setStroke(Color.web("#0099ff"));
        title.setStrokeWidth(1.5);
        title.setEffect(new javafx.scene.effect.DropShadow(25, Color.web("#00eaff")));

        // === Hiển thị điểm ===
        Text scoreText = new Text();
        scoreText.setFont(Font.font("Consolas", 30));
        scoreText.setFill(Color.web("#aeeaff"));
        scoreText.setEffect(new javafx.scene.effect.DropShadow(15, Color.web("#00ffff")));

        overlay.visibleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                scoreText.setText("Your Score: " + gameManager.getScore());
                 MainMenuScreen.highScoreManager.addScore(gameManager.getScore(), MainMenuScreen.highScoreManager.getChosenDifficulty());
                // System.out.println(MainMenuScreen.highScoreManager.getChosenDifficulty());
                // MainMenuScreen.highScoreManager.writeToFile();
            }
        });

        // === Nút bấm ===
        Button btnRestart = new Button("🔁 Restart");
        Button btnMainMenu = new Button("🏠 Main Menu");
        Button btnExit = new Button("🚪 Exit Game");

        for (Button b : new Button[]{btnRestart, btnMainMenu, btnExit}) {
            b.getStyleClass().add("game-button");
        }

        btnRestart.setOnAction(e -> {
            overlay.setVisible(false);
            stage.setScene(GameScreen.createScene(stage, levelPath));
        });

        btnMainMenu.setOnAction(e -> {
            try {
                new MainMenuScreen().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnExit.setOnAction(e -> {
            MainMenuScreen.highScoreManager.writeToFile();
            stage.close();
        });

        overlay.getChildren().addAll(title, scoreText, btnRestart, btnMainMenu, btnExit);
        return overlay;
    }

}
