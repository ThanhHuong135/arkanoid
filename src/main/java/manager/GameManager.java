package manager;

import animation.ItemFast;
import Ranking.HighScoreManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import animation.ItemDeath;
import javafx.animation.PauseTransition;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import level.Level;
import object.*;
import screens.MainMenuScreen;
import java.util.*;
public class GameManager {

    private Ball ball;
    private Paddle paddle;

    private List<Brick> bricks;
    private BrickType[][] levelBricks;
    private Map<BrickType, Color> brickColors = new HashMap<>();

    private double baseSpeed;
    private double basePaddleWidth;
    private PowerUpManager powerUpManager;
    private AnimationTimer gameLoop;

    private int score = 0;
    private int lives = 3;
    private int bricks_left = 0;
    private final double width = 700;
    private final double height = 650;
    boolean kiemtra = false;
    boolean flag = false;
    private boolean gameOver = false;
    private boolean explodePaddle = true;
    private ItemDeath itemDeath = new ItemDeath();
    private ItemFast itemFast = new ItemFast();
    private boolean paused = false;
    public int getScore() {
        return this.score;
    }

    public void init(String levelPath) {
        // Tạo paddle ở giữa, cách đáy 50px
        double paddleWidth = 120;
        double paddleHeight = 30;
        double paddleX = (width - paddleWidth) / 2;
        double paddleY = height - paddleHeight - 30; // cách mép dưới 30px

        paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);

        // Tạo bóng nằm giữa paddle, ngay phía trên
        double ballRadius = 10;
        double ballX = paddle.getX() + paddle.getWidth() / 2;
        double ballY = paddle.getY() - ballRadius - 1; // đặt sát trên paddle 1px
        double ballSpeed = 3.5;

        ball = new Ball(ballX, ballY, ballRadius, ballSpeed, 1, -1, true);

        baseSpeed = ball.getSpeed();
        basePaddleWidth = paddle.getWidth();

        powerUpManager = new PowerUpManager(this, ball, paddle, baseSpeed, basePaddleWidth);

        levelBricks = Level.load(levelPath);
        bricks = new ArrayList<>();
        double brickGap = 3;
        double brickWidth = (width - 9 * brickGap) / 8;
        double brickHeight = 30;
//        Color[] colors = {Color.web("#ff6b6b"), Color.web("#4ecdc4"),
//                Color.web("#ffe66d"), Color.web("#9d4edd")};

        for (int row = 0; row < levelBricks.length; row++) {
            for (int col = 0; col < levelBricks[row].length; col++) {
                double x = brickGap + col * (brickWidth + brickGap);
                double y = 50 + row * (brickHeight + brickGap);

                BrickType type = levelBricks[row][col];
                if (type != null) {
                    Color brickColor = brickColors.get(type);
                    if (brickColor == null) {}
                    bricks.add(new Brick(type, x, y, brickWidth, brickHeight));
                    bricks_left++;
                }
//                bricks.add(new Brick(x, y, brickWidth, brickHeight, 1, colors[(row * 6 + col) % colors.length]));
            }

        }
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);

        // bricks
        for (Brick b : bricks) {
            b.update();
            b.render(gc);
        }

        // Hiệu ứng nổ
        itemDeath.render(gc);

        // paddle & ball
        if (explodePaddle) {
            paddle.render(gc);
        } else if (itemDeath.isDone()) {  // <— thêm dòng này
            explodePaddle = true;         // paddle hiện lại sau khi nổ xong
        }

        ball.render(gc);
        powerUpManager.render(gc);
        itemFast.render(gc, paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        // score & lives
        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
        gc.setFill(Color.color(0, 0, 0, 0.6));
        gc.fillText("Score: " + score, 23, 28);
        gc.fillText("Lives: " + lives, width - 97, 28);
        gc.setFill(Color.web("#4FC3F7"));
        gc.fillText("Score: " + score, 20, 25);
        gc.fillText("Lives: " + lives, width - 100, 25);
    }


    public void startGame() {
        kiemtra = true;
    }

    public void update() {
        if (gameOver) {
            if (!flag) {
                int finalScore = score;
                MainMenuScreen.highScoreManager.addScore(score);
                MainMenuScreen.highScoreManager.writeToFile();
                flag = true;
            }
            return;
        }

        if (InputManager.isGameStarted()) {
            // bóng bay
        } else {
            // gắn bóng theo paddle
            ball.setX(paddle.getX() + paddle.getWidth() / 2);
            ball.setY(paddle.getY() - ball.getRadius());
            ball.setDx(1);
            ball.setDy(0);
        }

        if (paddle.isGoLeft()) paddle.moveLeft();
        if (paddle.isGoRight()) paddle.moveRight(width);

        if (!kiemtra && (paddle.isGoLeft() || paddle.isGoRight())) {
            kiemtra = true;
        }

        if (kiemtra) {
            // 1. Xử lý va chạm với tường
            double speed = ball.getSpeed();
            if (ball.getX() - ball.getRadius() < 0) {
                ball.setDx(Math.abs(ball.getDx()));
                ball.setX(ball.getRadius());
            }
            if (ball.getX() + ball.getRadius() > width) {
                ball.setDx(-Math.abs(ball.getDx()));
                ball.setX(width - ball.getRadius());
            }

            if (ball.getY() - ball.getRadius() < 0) {
                ball.setDy(Math.abs(ball.getDy()));
                ball.setY(ball.getRadius());
            }

            // 2. Va chạm với paddle
            if (ball.checkCollision(paddle) && ball.getDy() > 0) {
                ball.bounceOffPaddle(paddle);
                SoundManager.playHitSound();
            }

            // 3. Va chạm với gạch
            for (Brick b : bricks) {
                if (!b.isDestroyed() && ball.checkCollision(b)) {
                    ball.bounceOff(b);
                    ball.update();
                    b.takeHit();
                    score += 100;
                    SoundManager.playHitSound();
                    if (b.isDestroyed()) {
                        bricks_left--;
                        powerUpManager.spawn(b.getX() + b.getWidth()/2, b.getY());
                    }
                    break;
                }
            }

            // 4. Cập nhật vị trí bóng SAU khi xử lý va chạm
            ball.update();
        }

        List<PowerUp> powerUps = powerUpManager.getPowerUps();
        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p : powerUps) {
            p.update();

            // Nếu paddle hứng được power-up
            if (p.checkCollision(paddle)) {
                powerUpManager.applyPowerUp(p);
                if (p.getType().equals("DEATH")) {
                    double cx = p.getX() + p.getWidthDeath() / 2;       // chính giữa theo ngang
                    double cy = p.getY() + p.getHeightDeath();          // đáy item → chạm paddl
                    itemDeath.death(cx, cy);  // nổ tại vị trí chạm
                    explodePaddle = false;
                }
                else {
                  itemFast.fast(p.getType());
                }

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
            SoundManager.playGameOverSound();
            lives--;
            InputManager.waitForRestart();
            kiemtra = false;
            ball.clearTrail();

            if (lives > 0) {
                resetBall();
                resetPaddle();
                powerUpManager.reset();
            }
            else {
                gameOver = true;
            }
        }

        // hết gạch
//        System.out.println(bricks.size());
        if (bricks_left == 0) {
            gameOver = true;
        }
    }

    private void resetBall() {
        ball.setX(paddle.getX() + paddle.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getRadius());
        ball.setDx(1);
        ball.setDy(0);
        ball.setSpeed(baseSpeed);
    }

    private void resetPaddle() {
        paddle.setWidth(basePaddleWidth);
        double paddleX = (width - basePaddleWidth) / 2;
        double paddleY = height - paddle.getHeight() - 30;
        paddle.setX(paddleX);
        paddle.setY(paddleY);
    }

    public void setGameOver(boolean value) {
        this.gameOver = value;
    }

    public boolean isPaused() {
        return paused;
    }


    public void setGameLoop(Scene scene, GraphicsContext gc, String levelPath, VBox endGameMenu, VBox pauseMenu) {
        init(levelPath);
        InputManager.attach(scene, paddle, ball, this, pauseMenu);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused) {  // không pause thì update
                    update();
                    render(gc);
                }
                if (gameOver) {
                    if (!flag) {
                        SoundManager.playGameOverSound();
                        endGameMenu.setVisible(true);
                        //gameLoop.stop();
                        flag = true;
                    }
                    // Chỉ dừng gameLoop sau khi hiệu ứng nổ đã chạy hết
                    if (itemDeath.isDone()) {
                        gameLoop.stop();
                    } else {
                        render(gc); // vẫn render để hiển thị hiệu ứng nổ
                    }
                    return;
                }
            }
        };
        gameLoop.start();
    }


    public void pauseGame() {
        paused = true;
        powerUpManager.pauseEffects();
    }

    public void resumeGame() {
        paused = false;
        powerUpManager.resumeEffects();
    }
}
