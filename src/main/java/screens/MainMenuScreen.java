package screens;

import Ranking.HighScoreManager;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import object.Ball;
import object.Paddle;
import java.net.URL;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainMenuScreen extends Application {
    public static HighScoreManager highScoreManager = new HighScoreManager();


    public void start(Stage stage) {
        highScoreManager.readFromFile();

        // === VIDEO BACKGROUND ===
        URL videoPath = getClass().getResource("/assets/images/background.mp4");
        Media media = new Media(videoPath.toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(500);
        mediaView.setPreserveRatio(false);

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

        Label title = new Label("ARKANOID");
        title.getStyleClass().add("title-label");

        HBox content = new HBox(80, leftPane, rightPane);

        Button btnStart = createMenuButton("🚀 START GAME", "start-btn");
        btnStart.setOnAction(e -> {
            try {
                // Tạo Scene của GameScreen

//                Scene gameScene = GameScreen.createScene(stage);
//                stage.setScene(gameScene); // chuyển ngay sang GameScreen
//                stage.setTitle("Arkanoid - Game");  // set title cho GameScreen
                Scene levelScreen = LevelScreen.createScene(stage);
                stage.setScene(levelScreen);
                stage.setTitle("Arkanoid");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnSettings = createMenuButton("⚙ SETTING", "settings-btn");
        Button btnRanking = createMenuButton("🏆 RANKING", "ranking-btn");
        Button btnGuide = createMenuButton("📖 GUIDE", "guide-btn");
        Button btnExit = createMenuButton("❌ EXIT   ", "exit-btn");
        btnExit.setOnAction(e -> {
            highScoreManager.writeToFile();
            System.exit(0);
        });

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.6);"); // làm tối nền
        overlay.setVisible(false); // ẩn ban đầu

        //Create Ranking
        VBox highScorePane = createHighScorePane(overlay, content);
        VBox introductionPane = createIntroductionPane(overlay, content);
        overlay.getChildren().addAll(introductionPane, highScorePane);

        // Khi bấm “Hướng dẫn”
        btnGuide.setOnAction(e -> {
            overlay.setVisible(true);
            introductionPane.setVisible(true);

            // --- Hiệu ứng trượt lên ---
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), introductionPane);
            slideIn.setFromY(30);
            slideIn.setToY(0);

            // --- Hiệu ứng mờ dần ---
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), introductionPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            // --- Hiệu ứng phóng to ---
            javafx.animation.ScaleTransition scaleIn = new javafx.animation.ScaleTransition(Duration.millis(400), introductionPane);
            scaleIn.setFromX(0.9);
            scaleIn.setFromY(0.9);
            scaleIn.setToX(1.0);
            scaleIn.setToY(1.0);

            // --- Chạy đồng thời cả 3 ---
            javafx.animation.ParallelTransition show = new javafx.animation.ParallelTransition(slideIn, fadeIn, scaleIn);
            show.play();

            FadeTransition fadeBg = new FadeTransition(Duration.millis(400), content);
            fadeBg.setFromValue(1);
            fadeBg.setToValue(0.4);
            fadeBg.play();
        });

        btnRanking.setOnAction(e -> {
            overlay.setVisible(true);
            VBox pane = highScorePane;
            pane.setVisible(true);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), pane);
            slideIn.setFromY(30);
            slideIn.setToY(0);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            javafx.animation.ScaleTransition scaleIn = new javafx.animation.ScaleTransition(Duration.millis(400), pane);
            scaleIn.setFromX(0.9);
            scaleIn.setFromY(0.9);
            scaleIn.setToX(1.0);
            scaleIn.setToY(1.0);

            javafx.animation.ParallelTransition show = new javafx.animation.ParallelTransition(slideIn, fadeIn, scaleIn);
            show.play();

            FadeTransition fadeBg = new FadeTransition(Duration.millis(400), content);
            fadeBg.setFromValue(1);
            fadeBg.setToValue(0.4);
            fadeBg.play();
        });

        rightPane.getChildren().addAll(title, btnStart, btnSettings, btnRanking, btnGuide, btnExit);

        // MAIN LAYOUT
        StackPane root = new StackPane(mediaView, content, overlay);

        // Tạo SettingScreen
        SettingScreen settingScreen = new SettingScreen(content);
        // Thêm cả VBox menu và SettingScreen vào StackPane
        root.getChildren().add(settingScreen);
        // Nút Settings chỉ cần gọi show
        btnSettings.setOnAction(e -> settingScreen.show(content));

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(
                MainMenuScreen.class.getResource("/assets/style.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("Arkanoid");
        stage.show();
    }

    private Button createMenuButton(String text, String id) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setId(id);
        return button;
    }

    private VBox createIntroductionPane(StackPane overlay, HBox content) {
        //Heading
        Label heading = new Label("How to play");
        heading.setTextFill(Color.web("#b7e4c7"));
        heading.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
        heading.setEffect(new DropShadow(12, Color.web("#52b788", 0.35)));

        //BodyText
        Label bodyLabel = new Label("• Move the paddle with ← → keys\n" +
                "• Press SPACE to launch the ball\n" +
                "• Break all bricks to win\n" +
                "• Don’t let the ball fall below");
        bodyLabel.setFont(Font.font("Consolas", 14));
        bodyLabel.setWrapText(true);
        bodyLabel.setTextFill(Color.web("#e9f5ec"));
        bodyLabel.setStyle("-fx-font-size: 15px; -fx-line-spacing: 4px;");

        VBox introductionPane = new VBox(12);
        introductionPane.setAlignment(Pos.CENTER);
        introductionPane.setPadding(new Insets(20, 16, 24, 16));
        introductionPane.setMaxWidth(300);
        introductionPane.setMaxHeight(240);
        introductionPane.setBackground(new Background(new BackgroundFill(Color.web("#ffffff", 0.06), new CornerRadii(12), Insets.EMPTY)));
        introductionPane.setBorder(new Border(new BorderStroke(Color.web("#95d5b2", 0.35), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(1))));
        introductionPane.setEffect(new DropShadow(20, Color.web("#000000", 0.35)));
        introductionPane.setVisible(false);
        introductionPane.setOpacity(0);
        introductionPane.setTranslateY(30);
        introductionPane.setScaleX(0.9);
        introductionPane.setScaleY(0.9);

        // Nút X đóng
        Button closeGuide = new Button("✖");
        closeGuide.getStyleClass().add("close-button");

        HBox topBar = new HBox(closeGuide);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(5, 10, 0, 0));

        introductionPane.getChildren().addAll(topBar, heading, bodyLabel);

        // Khi bấm “Đóng”
        closeGuide.setOnAction(e -> {
            // --- Trượt xuống ---
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), introductionPane);
            slideOut.setFromY(0);
            slideOut.setToY(30);

            // --- Mờ dần ---
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), introductionPane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            // --- Thu nhỏ nhẹ ---
            javafx.animation.ScaleTransition scaleOut = new javafx.animation.ScaleTransition(Duration.millis(300), introductionPane);
            scaleOut.setFromX(1.0);
            scaleOut.setFromY(1.0);
            scaleOut.setToX(0.9);
            scaleOut.setToY(0.9);

            // --- Gộp 3 hiệu ứng ---
            javafx.animation.ParallelTransition hide = new javafx.animation.ParallelTransition(slideOut, fadeOut, scaleOut);
            hide.setOnFinished(ev -> {
                overlay.setVisible(false);
                introductionPane.setVisible(false);
            });
            hide.play();

            FadeTransition fadeBgBack = new FadeTransition(Duration.millis(300), content);
            fadeBgBack.setFromValue(0.4);
            fadeBgBack.setToValue(1);
            fadeBgBack.play();
        });

        return introductionPane;
    }

    private VBox createHighScorePane(StackPane overlay, HBox content) {
        //Heading
        Label heading = new Label("\uD83C\uDFC6 HIGH SCORES");
        heading.setTextFill(Color.web("#b7e4c7"));
        heading.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
        heading.setEffect(new DropShadow(12, Color.web("#52b788", 0.35)));

        //CloseButton
        Button closeBtn = new Button("✖");
        closeBtn.getStyleClass().add("close-button");

        HBox topBar = new HBox(closeBtn);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(5, 10, 0, 0));

        // Lấy danh sách điểm cho từng mức
        List<Integer> easyScores = MainMenuScreen.highScoreManager.getHighScoresEasy();
        List<Integer> mediumScores = MainMenuScreen.highScoreManager.getHighScoresMedium();
        List<Integer> hardScores = MainMenuScreen.highScoreManager.getHighScoresHard();

        VBox easyColumn = createScoreColumn("EASY", easyScores, "#b7e4c7");
        VBox mediumColumn = createScoreColumn("MEDIUM", mediumScores, "#ffe66d");
        VBox hardColumn = createScoreColumn("HARD", hardScores, "#ff6b6b");

        HBox scoreGrid = new HBox(40, easyColumn, mediumColumn, hardColumn);
        scoreGrid.setAlignment(Pos.CENTER);

        // --- Bảng hiển thị ---
        VBox pane = new VBox(12);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 16, 24, 16));
        pane.setMaxWidth(360);
        pane.setMaxHeight(300);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#ffffff", 0.06), new CornerRadii(12), Insets.EMPTY)));
        pane.setBorder(new Border(new BorderStroke(Color.web("#95d5b2", 0.35), BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(1))));
        pane.setEffect(new DropShadow(20, Color.web("#000000", 0.35)));
        pane.setVisible(false);
        pane.setOpacity(0);
        pane.setTranslateY(30);
        pane.setScaleX(0.9);
        pane.setScaleY(0.9);

        pane.getChildren().addAll(topBar, heading, scoreGrid);

        // --- Nút đóng ---
        closeBtn.setOnAction(e -> {
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), pane);
            slideOut.setFromY(0);
            slideOut.setToY(30);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            javafx.animation.ScaleTransition scaleOut = new javafx.animation.ScaleTransition(Duration.millis(300), pane);
            scaleOut.setFromX(1.0);
            scaleOut.setFromY(1.0);
            scaleOut.setToX(0.9);
            scaleOut.setToY(0.9);

            javafx.animation.ParallelTransition hide = new javafx.animation.ParallelTransition(slideOut, fadeOut, scaleOut);
            hide.setOnFinished(ev -> {
                overlay.setVisible(false);
                pane.setVisible(false);
            });
            hide.play();

            FadeTransition fadeBgBack = new FadeTransition(Duration.millis(300), content);
            fadeBgBack.setFromValue(0.4);
            fadeBgBack.setToValue(1);
            fadeBgBack.play();
        });

        return pane;
    }

    private VBox createScoreColumn(String title, List<Integer> scores, String colorHex) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web(colorHex));

        VBox column = new VBox(6);
        column.setAlignment(Pos.TOP_CENTER);
        column.getChildren().add(titleLabel);

        for (int i = 0; i < HighScoreManager.numScores; i++) {
            Label scoreLabel = new Label(String.format("%2d. %d", i + 1, scores.get(i)));
            scoreLabel.setFont(Font.font("Consolas", 14));
            scoreLabel.setTextFill(Color.web("#e9f5ec"));
            column.getChildren().add(scoreLabel);
        }

        return column;
    }

    private void drawDemo(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 300, 250);

        // Draw bricks
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

        // Ball
        Ball demoBall = new Ball(150, 120, 5, 0, 0, 0);
        demoBall.render(gc);

        // Paddle
        Paddle demoPaddle = new Paddle(120, 200, 60, 10);
        demoPaddle.render(gc);
    }
}
