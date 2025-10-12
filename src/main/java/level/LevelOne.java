package level;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Ball;
import object.Paddle;
import object.Brick;

import java.util.List;
import java.util.ArrayList;

public class LevelOne extends BaseLevel {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    boolean kiemtra = false;

    private boolean completed = false;


    public LevelOne(double width, double height) {
        super(width, height);
    }

    public Paddle getPaddle() {
        return paddle;
    }

    @Override
    public void init() {
        // Paddle nằm giữa màn hình, cách đáy 50px, rộng 120, cao 30
        paddle = new Paddle((width - 120) / 2, height - 50, 120, 30);

        // Ball nằm ngay trên paddle, radius 20
        ball = new Ball((width - 120) / 2 + 120 / 2, height - 50 - 20, 20, 3, 1, -1);

        // Brick
        bricks = new ArrayList<>();
        double brickGap = 5;
        double brickWidth = (width - 7 * brickGap) / 6;
        double brickHeight = 30;
        Color[] colors = {Color.web("#ff6b6b"), Color.web("#4ecdc4"),
                Color.web("#ffe66d"), Color.web("#9d4edd")};
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 6; col++) {
                double x = brickGap + col * (brickWidth + brickGap);
                double y = 50 + row * (brickHeight + brickGap);
                bricks.add(new Brick(x, y, brickWidth, brickHeight,1, colors[(row*6+col)%colors.length]));
            }
        }
    }

    @Override
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

    @Override

    public void update() {

        if (paddle.isGoLeft()) paddle.moveLeft();
        if (paddle.isGoRight()) paddle.moveRight(500);

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

      //  Va chạm bóng & bricks
        for (Brick b : bricks) {
          if (!b.isDestroyed() && ball.checkCollision(b)) {
               ball.bounceOff(b);
               b.takeHit();
               break;
           }
      }

    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
