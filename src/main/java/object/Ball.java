package object;

import animation.BallTrailEffect;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Ball extends MovableObject {
    private double speed;
    private double radius;
    private double directionX, directionY; // hướng
    private BallTrailEffect trailEffect; // lưu vệt đuôi

    public Ball(double x, double y, double radius, double speed, double directionX, double directionY) {
        super(x, y, radius * 2, radius * 2, directionX * speed, directionY * speed);
        this.radius = radius;
        this.speed = speed;
    }

    public Ball(double x, double y, double radius, double speed, double directionX, double directionY, boolean enableTrail) {
        super(x, y, radius * 2, radius * 2, directionX * speed, directionY * speed);
        this.radius = radius;
        this.speed = speed;

        if (enableTrail) {
            trailEffect = new BallTrailEffect(10, radius);
        }
    }

    public double getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void render(GraphicsContext gc) {
        // Vẽ vệt đuôi nếu có
        if (trailEffect != null) {
            trailEffect.render(gc);
        }
        // Vẽ bóng
        gc.setFill(Color.AQUA);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    @Override
    public void update() {
        if (trailEffect != null) {
            trailEffect.addPosition(x, y);
            trailEffect.update();   // cập nhật alpha điểm cũ
        }
        move(); // di chuyển bóng
    }

    public void clearTrail() {
        if (trailEffect != null) {
            trailEffect.clear();
        }
    }

    public void bounceOff(GameObject other) {
        /*if (x + radius > other.getX() && x - radius < other.getX() + other.getWidth()) {
            dy *= -1;
        } else {
            dx *= -1;
        }*/
        double speed = Math.sqrt(dx * dx + dy * dy);

        double objCenterX = other.getX() + other.getWidth() / 2;
        double objCenterY = other.getY() + other.getHeight() / 2;

        double diffX = x - objCenterX;
        double diffY = y - objCenterY;

        if (Math.abs(diffX) > Math.abs(diffY)) {
            dx = -dx; // va chạm ngang
        } else {
            dy = -dy; // va chạm dọc
        }

        // Giữ tốc độ
        double currentSpeed = Math.sqrt(dx * dx + dy * dy);
        dx = dx / currentSpeed * speed;
        dy = dy / currentSpeed * speed;
    }

    public void bounceOffPaddle(GameObject paddle) {
        double speed = 3.5;

        // Tính vị trí chạm (0 ở mép trái, 1 ở mép phải)
        double hitPos = (x - paddle.getX()) / paddle.getWidth();
        hitPos = Math.max(0, Math.min(1, hitPos)); // đảm bảo trong [0,1]
        double angle = Math.toRadians(-60 + 120 * hitPos);

        // Cập nhật vận tốc theo góc
        dx = speed * Math.sin(angle);
        dy = -speed * Math.cos(angle); // âm vì bóng bật lên
        if(paddle.y < y) dy = speed * Math.cos(angle);
    }


    public boolean checkCollision(GameObject obj) {
        return x + radius > obj.getX() &&
                x - radius < obj.getX() + obj.getWidth() &&
                y + radius > obj.getY() &&
                y - radius < obj.getY() + obj.getHeight();
    }
}
