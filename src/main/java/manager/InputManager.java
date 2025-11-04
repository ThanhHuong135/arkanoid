package manager;

import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import object.Paddle;
import object.Ball;

public class InputManager {
    private static boolean gameStarted = false; // Đã bấm Space chưa
    private static boolean waitingForRestart = false; // Đang đợi bấm Space sau khi chết

    public static void attach(Scene scene, Paddle paddle, Ball ball, GameManager gm, VBox pauseMenu) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT -> paddle.setGoLeft(true);
                    case RIGHT -> paddle.setGoRight(true);

                    case SPACE -> {
                        if (!gameStarted || waitingForRestart) {
                            gameStarted = true;
                            waitingForRestart = false;

                            double speed = ball.getSpeed();
                            double angle = Math.toRadians(Math.random() < 0.5 ? -45 : 45);
                            ball.setDx(speed * Math.sin(angle));
                            ball.setDy(-speed * Math.cos(angle)); // âm vì bóng đi lên
                            gm.startGame();
                        }
                    }

                    case P -> {  // ← phím Pause/Resume
                        if (gm.isPaused()) {
                            gm.resumeGame();
                            pauseMenu.setVisible(false);
                        } else {
                            gm.pauseGame();
                            pauseMenu.setVisible(true);
                        }
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT -> paddle.setGoLeft(false);
                    case RIGHT -> paddle.setGoRight(false);
                }
            }
        });
    }

    public static boolean isGameStarted() {
        return gameStarted;
    }

    public static void setGameStarted(boolean gameStarted) {
        InputManager.gameStarted = gameStarted;
    }

    public static void waitForRestart() {
        gameStarted = false;
        waitingForRestart = true;
    }

    public static void resetGame() {
        gameStarted = false;
        waitingForRestart = false;
    }
}
