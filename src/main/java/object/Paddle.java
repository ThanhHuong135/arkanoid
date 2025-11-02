package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Paddle extends MovableObject {

    private Image paddleImage;

    private boolean goLeft, goRight;

    private List<double[]> explosion = new ArrayList<>();

    public boolean isGoLeft() {
        return goLeft;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    public boolean isGoRight() {
        return goRight;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public Paddle(double x, double y) {
        super(x, y, 120, 60);
        loadImage();
    }

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        loadImage();
    }

    public void moveLeft() {
        if(this.getX() > 0) setX(getX() - 5);
    }

    // Di chuyển phải
    public void moveRight(double sceneWidth) {
       if(this.getX() + this.getWidth() < sceneWidth)
           setX(getX() + 5);// không ra ngoài phải

    }

    @Override
    public void update() {
        //
    }

    private void loadImage() {
        // Đường dẫn tương đối trong thư mục resources (nếu dùng Maven/Gradle)
        paddleImage = new Image(getClass().getResourceAsStream("/assets/paddle/paddle.png"));
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(paddleImage, x, y, width, height);
    }

    public void explode(double cx, double cy) {
        Random r = new Random();
        for (int i = 0; i < 160; i++) {
            double angle = r.nextDouble() * 2 * Math.PI;
            double speed = r.nextDouble() +0.3;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            explosion.add(new double[]{cx, cy, vx, vy, 180 + r.nextInt(60)}); // lifetime 60-120 frame ~1-2s
        }
    }

    // update + render
    public void renderExplosion(GraphicsContext gc) {
        for (Iterator<double[]> it = explosion.iterator(); it.hasNext(); ) {
            double[] p = it.next();
            p[0] += p[2]; // x
            p[1] += p[3]; // y
            p[4]--;       // lifetime
            gc.setGlobalAlpha(p[4] / 120.0);
            gc.setFill(Color.ORANGE);
            gc.fillOval(p[0], p[1], 10, 10);
            gc.setGlobalAlpha(1.0);
            if (p[4] <= 0) it.remove();
        }
    }

    public void triggerSpeedBlink() {
        // Mỗi lần gọi tạo 1 hiệu ứng viền xanh nhấp nháy
        double lifetime = 500; // khoảng 1 giây (60 frame)
        // Đặt flag RGB = (0,1,1) để renderExplosion() nhận biết là viền
        explosion.add(new double[]{0, 0, 0, 0, lifetime, 0, 1, 1});
    }

    public void renderBlink(GraphicsContext gc) {
        for (Iterator<double[]> it = explosion.iterator(); it.hasNext();) {
            double[] p = it.next();

            // chỉ vẽ nếu là hiệu ứng viền
            if (p.length >= 8 && p[5] == 0 && p[6] == 1 && p[7] == 1) {
                p[4]--;
                if (p[4] <= 0) { it.remove(); continue; }

                double alpha = 0.6 + 0.4 * Math.sin(System.currentTimeMillis() * 0.02);
                gc.setGlobalAlpha(alpha);
                gc.setStroke(Color.CYAN);
                gc.setLineWidth(4);
                gc.strokeRoundRect(x - 3, y - 3, width + 6, height + 6, 25, 25);
                gc.setGlobalAlpha(1.0);
            }
        }
    }


}
