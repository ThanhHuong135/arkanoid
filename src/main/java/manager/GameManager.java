package manager;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Ball;
import object.Brick;
import object.Paddle;
import object.PowerUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;

    private int score = 0;
    private int lives = 3;

    private final double width = 700;
    private final double height = 650;
    boolean kiemtra = false;
    private boolean gameOver = false;

    public void init() {
        paddle = new Paddle((width - 120) / 2, height - 50, 120, 20);
        ball = new Ball((width - 120) / 2 + 60, height - 60, 10, 3, 1, -1, true);

        powerUps = new ArrayList<>();
        bricks = new ArrayList<>();
        double brickGap = 5;
        double brickWidth = (width - 9 * brickGap) / 8;
        double brickHeight = 30;
        Color[] colors = {Color.web("#ff6b6b"), Color.web("#4ecdc4"),
                Color.web("#ffe66d"), Color.web("#9d4edd")};

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                double x = brickGap + col * (brickWidth + brickGap);
                double y = 50 + row * (brickHeight + brickGap);
                bricks.add(new Brick(x, y, brickWidth, brickHeight, 1, colors[(row * 6 + col) % colors.length]));
            }
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.web("#0b0b28"));
        gc.fillRect(0, 0, width, height);

        // bricks
        for (Brick b : bricks) {
            b.update();
            b.render(gc);
        }

        // paddle & ball
        paddle.render(gc);
        ball.render(gc);

        // power-ups
        for (PowerUp p : powerUps) {
            p.render(gc);
        }

        // score & lives
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 20, 25);
        gc.fillText("Lives: " + lives, width - 100, 25);
    }

    public void update() {
        if (gameOver) return;

        if (paddle.isGoLeft()) paddle.moveLeft();
        if (paddle.isGoRight()) paddle.moveRight(width);

        if (kiemtra == false && (paddle.isGoLeft() || paddle.isGoRight())) {
            ball.update();
            kiemtra = true;
        }
        if (kiemtra) ball.update();

        double speed = ball.getSpeed();
        if (ball.getX() - ball.getRadius() < 0) {
            ball.setDx(Math.abs(ball.getDx()));
            ball.setX(ball.getRadius());
        }
        if (ball.getX() + ball.getRadius() > width) {
            ball.setDx(-Math.abs(ball.getDx()));
            ball.setX(width - ball.getRadius());
        }

        // Trần
        if (ball.getY() - ball.getRadius() < 0) {
            ball.setDy(Math.abs(ball.getDy()));
            ball.setY(ball.getRadius());
        }

        // Va chạm bóng & paddle
        if (ball.checkCollision(paddle) && ball.getDy() > 0) {
            ball.bounceOffPaddle(paddle);
        }

        // Va chạm bóng & bricks
        for (Brick b : bricks) {
            if (!b.isDestroyed() && ball.checkCollision(b)) {
                ball.bounceOff(b);
                b.takeHit();

                score += 100;

                if (b.isDestroyed()) {
                    Random r = new Random();
                    int chance = r.nextInt(100);
                    if (chance < 30) {
                        String[] types = {"FAST_BALL", "DEATH"};
                        String type = types[r.nextInt(types.length)];
                        powerUps.add(new PowerUp(b.getX() + b.getWidth() / 2, b.getY(), type));
                    }
                }
                break;
            }
        }


        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p : powerUps) {
            p.update();

            // Nếu paddle hứng được power-up
            if (p.checkCollision(paddle)) {
                applyPowerUp(p);
                toRemove.add(p); // đánh dấu để xóa sau
            }
            // Nếu power-up rơi khỏi màn hình
            else if (p.getY() > height) {
                toRemove.add(p);
            }
        }

        // Xóa các power-up đã dùng hoặc rơi khỏi màn hình
        powerUps.removeAll(toRemove);

        // bóng rơi ra khỏi màn hình
        if (ball.getY() > height) {
            lives--;
            kiemtra = false;
            ball.clearTrail();
            if (lives > 0) {
                resetBall();;
            }
            else gameOver = true;
        }
    }

    private void resetBall() {
        ball.setX(paddle.getX() + paddle.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getRadius());
        ball.setDy(-Math.abs(ball.getDy()));
    }


    private void applyPowerUp(PowerUp p) {
        String type = p.getType();

        switch (type) {
            case "FAST_BALL":
                ball.setSpeed(ball.getSpeed() * 1.3);
                break;
            case "DEATH":
                gameOver = true;
                break;
            default:
                // Nếu có loại mới trong tương lai
                break;
        }
    }


    public void setGameLoop(Scene scene, GraphicsContext gc) {
            init();
            InputManager.attach(scene, paddle);

            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                    render(gc);
                }
            }.start();
        }
    }
