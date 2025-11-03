package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import manager.GameManager;

/**
 * Màn hình end game trong suốt phủ lên GameScreen.
 */
public class EndGameScreen {

    public static StackPane createOverlay(Stage stage, String levelPath) {
        // Lớp nền trong suốt phủ toàn màn hình
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setVisible(false);
        overlay.setPickOnBounds(true);

        // Hộp chứa menu (3 nút)
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(15));
        menuBox.getStyleClass().add("endgame-menu");

        Button btnRestart = new Button("🔁 Restart");
        Button btnMainMenu = new Button("🏠 Main Menu");
        Button btnExit = new Button("🚪 Exit Game");

        for (Button b : new Button[]{btnRestart, btnMainMenu, btnExit}) {
            b.getStyleClass().add("endgame-button");
        }

        // Hành động nút
        btnRestart.setOnAction(e -> {
            overlay.setVisible(false);
            stage.setScene(GameScreen.createScene(stage, levelPath));
        });

        btnMainMenu.setOnAction(e -> {
            try {
                MainMenuScreen main = new MainMenuScreen();
                main.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnExit.setOnAction(e -> stage.close());

        menuBox.getChildren().addAll(btnRestart, btnMainMenu, btnExit);

        // Bọc menuBox trong StackPane để căn giữa dễ hơn
        overlay.getChildren().add(menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER);

        return overlay;
    }

}
