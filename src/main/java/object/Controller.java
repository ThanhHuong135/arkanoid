package object;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import level.LevelOne;
import object.Ball;
import object.Paddle;
import java.util.ArrayList;
import java.util.List;
import object.Brick;
import animation.BrickParticle;

public class Controller {

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    public double width = 700;
    public double height = 650;
    boolean kiemtra = false;

    public void init() {
        // Paddle nằm giữa màn hình, cách đáy 50px, rộng 120, cao 30
        paddle = new Paddle((width - 120) / 2, height - 50, 120, 20);

        // Ball nằm ngay trên paddle, radius 20
        ball = new Ball((width - 120) / 2 + 120 / 2, height - 50 - 10, 10, 3, 1, -1);

        // Brick
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
                bricks.add(new Brick(x, y, brickWidth, brickHeight,1, colors[(row*6+col)%colors.length]));
            }
        }
    }

    public void render(GraphicsContext gc) {
        // Nền tối
        gc.setFill(Color.web("#0b0b28"));
        gc.fillRect(0, 0, width, height);

        // Bricks
        for (Brick b : bricks) {
            b.render(gc);
        }

        // Ball + Paddle
        ball.render(gc);
        paddle.render(gc);
    }

    public void update() {
        if (paddle.isGoLeft()) paddle.moveLeft();
        if (paddle.isGoRight()) paddle.moveRight(this.width);

        if(kiemtra==false && (paddle.isGoLeft() || paddle.isGoRight() ) ){ball.update();kiemtra=true;}
        if(kiemtra) ball.update();

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
            if (!b.isDestroyed()) {
                b.update(); // ← Gọi update để xử lý hiệu ứng vỡ
                if (!b.isDestroyed() && ball.checkCollision(b)) {
                    ball.bounceOff(b);
                    b.takeHit();
                    break;
                }
            }
        }

    }
    public void setGameLoop(Scene scene, GraphicsContext gc) {
//        LevelOne levelOne = new LevelOne(500, 650);
        init();

        // --- Xử lý di chuyển paddle bằng bàn phím ---
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        paddle.setGoLeft(true);
                        break;
                    case RIGHT:
                        paddle.setGoRight(true);
                        break;
//                    case SPACE:
//                        gamestarted = true;
//                        break;
//                    case ESCAPE:
//                        gamestarted = false;
//                        break;
                    default:
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        paddle.setGoLeft(false);
                        break;
                    case RIGHT:
                        paddle.setGoRight(false);
                        break;
                    default:
                        break;
                }
            }
        });

        // Animation loop để render LevelOne
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();  // hiện tại chưa logic
                render(gc);
            }
        }.start();
    }
}
