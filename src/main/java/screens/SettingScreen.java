package screens;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import manager.SoundManager;

public class SettingScreen extends StackPane {

    private VBox settingBox;

    // Constructor
    public SettingScreen(Pane mainContent) {
        // Nền mờ overlay
        this.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        this.setVisible(false);

        // === Hộp cài đặt ===
        settingBox = new VBox(15);
        settingBox.setAlignment(Pos.CENTER);
        settingBox.setPadding(new Insets(20, 16, 24, 16));
        settingBox.setMaxWidth(320);
        settingBox.setBackground(new Background(new BackgroundFill(
                Color.web("#ffffff", 0.07), new CornerRadii(12), Insets.EMPTY)));
        settingBox.setBorder(new Border(new BorderStroke(
                Color.web("#95d5b2", 0.4), BorderStrokeStyle.SOLID,
                new CornerRadii(12), new BorderWidths(1))));
        settingBox.setEffect(new DropShadow(20, Color.web("#000000", 0.4)));
        settingBox.setOpacity(0);
        settingBox.setTranslateY(30);
        settingBox.setScaleX(0.9);
        settingBox.setScaleY(0.9);

        // --- Header ---
        Label title = new Label("Settings");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#b7e4c7"));

        // --- Volume slider ---
        Label volLabel = new Label("Volume");
        volLabel.setTextFill(Color.web("#e9f5ec"));
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.25);
        volumeSlider.setBlockIncrement(0.1);

        volumeSlider.valueProperty().addListener((obs, oldV, newV) -> {
            SoundManager.setVolume(newV.doubleValue());
        });

        // --- Mute toggle ---
        CheckBox muteBox = new CheckBox("Mute all sounds");
        muteBox.setTextFill(Color.web("#e9f5ec"));
        muteBox.setOnAction(e -> SoundManager.setMute(muteBox.isSelected()));

        /*// --- Option khác ---
        CheckBox retroBox = new CheckBox("Retro visual mode");
        retroBox.setTextFill(Color.web("#e9f5ec"));*/

        // --- Nút Close ---
        Button btnClose = new Button("✖");
        btnClose.getStyleClass().add("close-button");
        HBox topBar = new HBox(btnClose);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(5, 10, 0, 0));

        settingBox.getChildren().addAll(topBar, title, volLabel, volumeSlider, muteBox);
        this.getChildren().add(settingBox);

        // ====== Hiệu ứng ======
        btnClose.setOnAction(e -> hide(mainContent));
    }

    public void show(Pane mainContent) {
        this.setVisible(true);

        // Hiệu ứng bật
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), settingBox);
        slideIn.setFromY(30);
        slideIn.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), settingBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(400), settingBox);
        scaleIn.setFromX(0.9);
        scaleIn.setFromY(0.9);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        new ParallelTransition(slideIn, fadeIn, scaleIn).play();

        FadeTransition fadeBg = new FadeTransition(Duration.millis(400), mainContent);
        fadeBg.setFromValue(1);
        fadeBg.setToValue(0.4);
        fadeBg.play();
    }

    public void hide(Pane mainContent) {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), settingBox);
        slideOut.setFromY(0);
        slideOut.setToY(30);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), settingBox);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), settingBox);
        scaleOut.setFromX(1.0);
        scaleOut.setFromY(1.0);
        scaleOut.setToX(0.9);
        scaleOut.setToY(0.9);

        ParallelTransition hide = new ParallelTransition(slideOut, fadeOut, scaleOut);
        hide.setOnFinished(ev -> this.setVisible(false));
        hide.play();

        FadeTransition fadeBgBack = new FadeTransition(Duration.millis(300), mainContent);
        fadeBgBack.setFromValue(0.4);
        fadeBgBack.setToValue(1);
        fadeBgBack.play();
    }
}
