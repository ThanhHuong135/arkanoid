package screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import object.Ball;
import object.Paddle;

import java.util.concurrent.atomic.AtomicReference;

public class LevelScreen {

    public static Scene createScene(Stage stage) {
        // LEFT: Game Demo
        VBox leftPane = new VBox();
        leftPane.setSpacing(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.getStyleClass().add("left-pane");

        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawDemo(gc);

        Label statusLabel = new Label("SCORE: 0    LEVEL: 0    LIVES: ♥ ♥ ♥");
        statusLabel.setTextFill(Color.CYAN);
        statusLabel.setFont(Font.font("Arial", 14));
        leftPane.getChildren().addAll(canvas, statusLabel);

        // RIGHT: Menu Buttons
        VBox rightPane = new VBox();
        rightPane.setSpacing(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.getStyleClass().add("right-pane");

        Label title = new Label("CHỌN MỨC ĐỘ");
        title.getStyleClass().add("title-label");

        AtomicReference<String> levelPath = new AtomicReference<>("level_1.csv");

        Button btnStart = createMenuButton("🚀 BẮT ĐẦU CHƠI", "start-btn");
        btnStart.setOnAction(e -> {
            try {
                // Tạo Scene của GameScreen
                Scene gameScene = GameScreen.createScene(stage, levelPath.get());
                stage.setScene(gameScene); // chuyển ngay sang GameScreen
                stage.setTitle("Arkanoid");  // set title cho GameScreen

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnEasy = createMenuButton("EASY", "easy-btn");
        btnEasy.setOnAction(e -> {
            try  {
                levelPath.set("level_1.csv");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Button btnNormal = createMenuButton("NORMAL", "normal-btn");
        btnNormal.setOnAction(e -> {
            try {
                levelPath.set("level_2.csv");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Button btnHard = createMenuButton("HARD", "hard-btn");
        btnHard.setOnAction(e -> {
            try {
                levelPath.set("level_3.csv");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Button btnBack = createMenuButton("⬅ QUAY LẠI", "back-btn");
        btnBack.setOnAction(e -> {
            try {
                MainMenuScreen mainMenuScreen = new MainMenuScreen();
                mainMenuScreen.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        rightPane.getChildren().addAll(title, btnStart, btnEasy, btnNormal, btnHard, btnBack);

        // MAIN LAYOUT
        HBox root = new HBox(80, leftPane, rightPane);
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );
        return scene;
    }

    private static Button createMenuButton(String text, String id) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setId(id);
        return button;
    }

    private static void drawDemo(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 300, 250);

        Color[] colors = {
                Color.web("#ff6b6b"),
                Color.web("#4ecdc4"),
                Color.web("#ffe66d"),
                Color.web("#9d4edd")
        };
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 8; col++) {
                gc.setFill(colors[(row + col) % colors.length]);
                gc.fillRect(20 + col * 32, 20 + row * 25, 30, 20);
            }
        }

        Ball demoBall = new Ball(150, 120, 5, 0, 0, 0);
        demoBall.render(gc);

        Paddle demoPaddle = new Paddle(120, 200, 60, 10);
        demoPaddle.render(gc);
    }
}
