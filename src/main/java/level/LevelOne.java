package level;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Ball;
import object.Paddle;

public class LevelOne extends BaseLevel {
    private Ball ball;
    private Paddle paddle;
    private boolean completed = false;

    public LevelOne(double width, double height) {
        super(width, height);
    }

    @Override
    public void init() {
        // Paddle nằm giữa màn hình, cách đáy 50px, rộng 120, cao 30
        paddle = new Paddle((width - 120) / 2, height - 50, 120, 30);

        // Ball nằm ngay trên paddle, radius 20
        ball = new Ball((width - 120) / 2 + 120 / 2, height - 50 - 20, 20, 2, 2, 0);
    }

    @Override
    public void render(GraphicsContext gc) {
        // Nền tối
        gc.setFill(Color.web("#0b0b28"));
        gc.fillRect(0, 0, width, height);

        // Brick demo
        double brickGap = 5; // khoảng cách giữa các brick
        double brickWidth = (width - 7 * brickGap) / 6;
        double brickHeight = 40;
        Color[] colors = {  Color.web("#ff6b6b"),
                            Color.web("#4ecdc4"),
                            Color.web("#ffe66d"),
                            Color.web("#9d4edd") };
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 6; col++) {
                double x = brickGap + col * (brickWidth + brickGap);
                double y = 50 + row * (brickHeight + brickGap);
                gc.setFill(colors[(row + col) % colors.length]); // xen kẽ
                gc.fillRect(x, y, brickWidth, brickHeight);
            }
        }

        // Ball + Paddle
        ball.render(gc);
        paddle.render(gc);
    }

    @Override
    public void update() {
        //ball.update();
        // TODO: thêm logic di chuyển paddle, va chạm, kiểm tra hết brick
        // Khi hết brick, completed = true
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
