package manager;

import animation.BlinkingEffect;
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
    private PowerUpManager powerUpManager;

    private double baseSpeed;
    private double basePaddleWidth;

    private PauseTransition ballTimer;
    private PauseTransition paddleTimer;
    private GameManager gameManager;

    private BlinkingEffect effect = new BlinkingEffect();

    public PowerUpManager(GameManager gameManager, Ball ball, Paddle paddle, double baseSpeed, double basePaddleWidth) {
        this.gameManager = gameManager;
        this.ball = ball;
        this.paddle = paddle;
        this.baseSpeed = baseSpeed;
        this.basePaddleWidth = basePaddleWidth;
    }

    public void spawn(double x, double y) {
        Random r = new Random();

        if (r.nextInt(100) < 30) {
            int roll = r.nextInt(100);
            String type;
            if (roll < 30) type = "DEATH";
            else if (roll < 50) type = "BIG_PADDLE";
            else if (roll < 80) type = "FAST_BALL";
            else type = "SMALL_PADDLE";
            powerUps.add(new PowerUp(x, y, type));
        }
    }

    public void update() {
        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p : powerUps) {
            p.update();

            if (p.checkCollision(paddle) && p.isActive()) {
                SoundManager.playHitSound();
                applyPowerUp(p);
                toRemove.add(p);
            }
            // PowerUp rơi khỏi màn hình
            else if (p.getY() > 650) { // height màn hình
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

    public void applyPowerUp(PowerUp p) {
        String type = p.getType();

        switch (type) {
            case "FAST_BALL":
                ball.setSpeed(ball.getSpeed() * 1.5);

                if (ballTimer != null) ballTimer.stop();
                ballTimer = new PauseTransition(Duration.seconds(10));

                ballTimer.setOnFinished(e -> {
                    ball.setSpeed(baseSpeed);
                });
                ballTimer.play();

                effect.applyBlinkingEffect(ball, Color.DEEPSKYBLUE, Color.AQUA, 10, 3);
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
                if (paddleTimer != null) paddleTimer.stop();
                paddleTimer = new PauseTransition(Duration.seconds(10));
                paddleTimer.setOnFinished(e -> {
                    paddle.setWidth(basePaddleWidth);
                });
                paddleTimer.play();
                break;

            default:
                //
                break;
        }
        p.deactivate();
    }
    public void reset() {
        powerUps.clear();

        // Dừng timer và reset trạng thái
        if (ballTimer != null) {
            ballTimer.stop();
            ball.setSpeed(baseSpeed);
        }

        if (paddleTimer != null) {
            paddleTimer.stop();
            paddle.setWidth(basePaddleWidth);
        }

        effect.stop();
        ball.setColor(Color.AQUA);
    }

    public void pauseEffects() {
        if (ballTimer != null) ballTimer.pause();
        if (paddleTimer != null) paddleTimer.pause();
    }

    public void resumeEffects() {
        if (ballTimer != null) ballTimer.play();
        if (paddleTimer != null) paddleTimer.play();
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }


}
