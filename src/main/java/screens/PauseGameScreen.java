package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import manager.GameManager;
import manager.InputManager;
public class PauseGameScreen {

    public static VBox createMenu(Stage stage, GameManager gameManager, InputManager inputManager, String levelPath) {
        // --- Tạo layout menu pause ---
        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(20));
        menuBox.setPrefWidth(200);   // rộng 400px
        menuBox.setPrefHeight(250);
        menuBox.setStyle("-fx-background-color: transparent;");
        menuBox.setVisible(false); // ban đầu ẩn

        // --- Các nút ---
        Button btnResume = new Button("▶ Resume");
        Button btnRestart = new Button("🔁 Restart");
        Button btnMainMenu = new Button("🏠 Main Menu");

        for (Button b : new Button[]{btnResume, btnRestart, btnMainMenu}) {
            b.getStyleClass().add("game-button");
        }

        menuBox.getChildren().addAll(btnResume, btnRestart, btnMainMenu);

        // --- Xử lý các nút ---
        btnResume.setOnAction(e -> {
            menuBox.setVisible(false);
            gameManager.resumeGame();
        });

        btnRestart.setOnAction(e -> {
            menuBox.setVisible(false);
            stage.setScene(GameScreen.createScene(stage, levelPath));
            inputManager.setGameStarted(false) ;
        });

        btnMainMenu.setOnAction(e -> {
            try {
                MainMenuScreen main = new MainMenuScreen();
                main.start(stage);
                inputManager.setGameStarted(false) ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return menuBox;
    }
}
