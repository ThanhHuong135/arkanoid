package manager;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import object.Ball;
import object.Paddle;
import object.PowerUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpManager {
    private List<PowerUp> powerUps = new ArrayList<>();
    private Ball ball;
    private Paddle paddle;

    private double baseSpeed;
    private double basePaddleWidth;

    private PauseTransition ballTimer;
    private PauseTransition paddleTimer;
    private GameManager gameManager;

    public PowerUpManager(GameManager gameManager, Ball ball, Paddle paddle, double baseSpeed, double basePaddleWidth) {
        this.gameManager = gameManager;
        this.ball = ball;
        this.paddle = paddle;
        this.baseSpeed = baseSpeed;
        this.basePaddleWidth = basePaddleWidth;
    }

    public void spawn(double x, double y) {
        Random r = new Random();

        // Xác suất rơi PowerUp tổng
        if (r.nextInt(100) < 30) {  // 30% tổng số gạch rơi PowerUp
            int roll = r.nextInt(100);
            String type;
            if (roll < 40) type = "DEATH";           // 40%
            else if (roll < 60) type = "BIG_PADDLE"; // 20%
            else if (roll < 80) type = "FAST_BALL";  // 20%
            else if (roll < 90) type = "SLOW_BALL";  // 10%
            else type = "SMALL_PADDLE";
            powerUps.add(new PowerUp(x, y, type));
        }
    }

    public void update() {
        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p : powerUps) {
            p.update();

            // Paddle ăn PowerUp
            if (p.checkCollision(paddle) && p.isActive()) {
                applyPowerUp(p);
                toRemove.add(p);
            }
            // PowerUp rơi khỏi màn hình
            else if (p.getY() > 650) { // height của màn hình
                toRemove.add(p);
            }
        }
        powerUps.removeAll(toRemove);
    }

    public void render(GraphicsContext gc) {
        for (PowerUp p : powerUps) {
            p.render(gc);
        }
    }

    private void applyPowerUp(PowerUp p) {
        String type = p.getType();

        switch (type) {
            case "FAST_BALL":
                ball.setSpeed(ball.getSpeed() * 5);

                if (ballTimer != null) ballTimer.stop();
                ballTimer = new PauseTransition(Duration.seconds(10));

                ballTimer.setOnFinished(e -> {
                    ball.setSpeed(baseSpeed);
                });
                ballTimer.play();

                applyBlinkingEffect(ball, Color.DEEPSKYBLUE, Color.AQUA, 10, 3);
                break;

            case "SLOW_BALL":
                ball.setSpeed(baseSpeed * 0.25);

                if (ballTimer != null) ballTimer.stop();
                ballTimer = new PauseTransition(Duration.seconds(10));
                ballTimer.setOnFinished(e -> {
                    ball.setSpeed(baseSpeed);
                });
                ballTimer.play();
                applyBlinkingEffect(ball, Color.LIGHTGREEN, Color.AQUA, 10, 3);
                break;

            case "DEATH":
                gameManager.setGameOver(true);
                break;

            case "BIG_PADDLE":
                paddle.increaseWidth();

                if (paddleTimer != null) paddleTimer.stop();

                paddleTimer = new PauseTransition(Duration.seconds(10));
                paddleTimer.setOnFinished(e -> paddle.setWidth(basePaddleWidth));
                paddleTimer.play();
                break;

            case "SMALL_PADDLE":
                paddle.setWidth(basePaddleWidth * 0.7);
                //paddle.setColor(Color.YELLOW);
                if (paddleTimer != null) paddleTimer.stop();
                paddleTimer = new PauseTransition(Duration.seconds(10));
                paddleTimer.setOnFinished(e -> {
                    paddle.setWidth(basePaddleWidth);
                });
                paddleTimer.play();
                break;

            default:
                // Nếu có loại mới trong tương lai
                break;
        }
        p.deactivate();
    }

    private void applyBlinkingEffect(Object target, Color activeColor, Color originalColor, double duration, double blinkDuration) {
        //Màu ăn PowerUp
        setColor(target, activeColor);

        //Timeline nhấp nháy 3s cuối
        double interval = 0.25; // nhấp nháy mỗi 0.25s
        int totalBlinkFrames = (int) (blinkDuration / interval);

        Timeline blink = new Timeline();
        for (int i = 0; i < totalBlinkFrames; i++) {
            int frame = i;
            blink.getKeyFrames().add(new KeyFrame(Duration.seconds(frame * interval), e -> {
                // Nhấp nháy: màu powerUp và màu gốc
                if (frame % 2 == 0) setColor(target, activeColor);
                else setColor(target, originalColor);
            }));
        }

        // Delay trước khi nhấp nháy
        PauseTransition delay = new PauseTransition(Duration.seconds(duration - blinkDuration));
        delay.setOnFinished(e -> blink.play());
        delay.play();

        // Khi PowerUp hết → reset màu về gốc
        PauseTransition end = new PauseTransition(Duration.seconds(duration));
        end.setOnFinished(e -> setColor(target, originalColor));
        end.play();
    }

    private void setColor(Object target, Color color) {
        ((Ball) target).setColor(color);
    }

}
