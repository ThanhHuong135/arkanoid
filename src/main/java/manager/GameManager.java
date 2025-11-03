package manager;

import animation.ItemFast;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import animation.ItemDeath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import level.Level;
import object.*;


import java.util.*;


public class GameManager {

    private Ball ball;
    private Paddle paddle;

    private List<Brick> bricks;
    private BrickType[][] levelBricks;
    private Map<BrickType, Color> brickColors = new HashMap<>();


    private List<PowerUp> powerUps;

    private int score = 0;
    private int lives = 3;

    private final double width = 700;
    private final double height = 650;
    boolean kiemtra = false;
    private boolean gameOver = false;
    private double baseSpeed; // tốc độ gốc của bóng
    private double basePaddleWidth; // chiều rộng gốc của paddle
    private boolean explodePaddle = true;
    private ItemDeath itemDeath = new ItemDeath();
    private ItemFast itemFast = new ItemFast();

    public void init() {
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
        double ballSpeed = 1.5;

        ball = new Ball(ballX, ballY, ballRadius, ballSpeed, 1, -1, true);

        baseSpeed = ball.getSpeed();

        powerUps = new ArrayList<>();


        levelBricks = Level.load("level_1.csv");

        bricks = new ArrayList<>();
        double brickGap = 3;
        double brickWidth = (width - 9 * brickGap) / 8;
        double brickHeight = 30;
        Color[] colors = {Color.web("#ff6b6b"), Color.web("#4ecdc4"),
                Color.web("#ffe66d"), Color.web("#9d4edd")};

        for (int row = 0; row < levelBricks.length; row++) {
            for (int col = 0; col < levelBricks[row].length; col++) {
                double x = brickGap + col * (brickWidth + brickGap);
                double y = 50 + row * (brickHeight + brickGap);

                BrickType type = levelBricks[row][col];
                if (type != null) {
                    Color brickColor = brickColors.get(type);
                    if (brickColor == null) {}
                    bricks.add(new Brick(type, x, y, brickWidth, brickHeight));
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

        // paddle & ball
        if(explodePaddle == true){
        paddle.render(gc);
        }

        ball.render(gc);

        // power-ups
        for (PowerUp p : powerUps) {
            p.render(gc);
        }

        // score & lives
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 20, 25);
        gc.fillText("Lives: " + lives, width - 100, 25);
        itemDeath.render(gc);
        itemFast.render(gc,paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight() );
    }

    public void startGame() {
        kiemtra = true;
    }

    public void update(GraphicsContext gc) {
        if (gameOver) return;

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
            }

            // 3. Va chạm với gạch
            for (Brick b : bricks) {
                if (!b.isDestroyed() && ball.checkCollision(b)) {
                    ball.bounceOff(b);
                    ball.update();
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

            // 4. Cập nhật vị trí bóng SAU khi xử lý va chạm
            ball.update();
        }



        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p : powerUps) {
            p.update();

            // Nếu paddle hứng được power-up
            if (p.checkCollision(paddle)) {
                applyPowerUp(p);
                if(p.getType() == "DEATH") {
                    double cx = p.getX() + p.getWidthDeath() / 2;       // chính giữa theo ngang
                    double cy = p.getY() + p.getHeightDeath();          // đáy item → chạm paddle
                    itemDeath.death(cx, cy);  // nổ tại vị trí chạm
                    explodePaddle = false;
                }
                else {
                  itemFast.fast();
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
            lives--;
            InputManager.waitForRestart();
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
        ball.setDx(1);
        ball.setDy(0);
        ball.setSpeed(baseSpeed);
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
            InputManager.attach(scene, paddle, ball, this);

            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update(gc);
                    render(gc);
                }
            }.start();
        }
}
