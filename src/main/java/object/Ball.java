package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends MovableObject {
    private double speed;
    private double radius;
    private double directionX, directionY; // hướng

    public Ball(double x, double y, double radius, double speed, double directionX, double directionY) {
        super(x, y, radius * 2, radius * 2, directionX * speed, directionY * speed);
        this.radius = radius;
        this.speed = speed;
    }

    public double getRadius(){
        return radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.AQUA);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    @Override
    public void update() {
        move(); // di chuyển bóng
    }

    public void bounceOff(GameObject other) {
        /*if (x + radius > other.getX() && x - radius < other.getX() + other.getWidth()) {
            dy *= -1;
        } else {
            dx *= -1;
        }*/
        double speed = Math.sqrt(dx*dx + dy*dy);

        double objCenterX = other.getX() + other.getWidth()/2;
        double objCenterY = other.getY() + other.getHeight()/2;

        double diffX = x - objCenterX;
        double diffY = y - objCenterY;

        if (Math.abs(diffX) > Math.abs(diffY)) {
            dx = -dx; // va chạm ngang
        } else {
            dy = -dy; // va chạm dọc
        }

        // Giữ tốc độ
        double currentSpeed = Math.sqrt(dx*dx + dy*dy);
        dx = dx / currentSpeed * speed;
        dy = dy / currentSpeed * speed;
    }

    public void bounceOffPaddle(GameObject paddle) {
        double speed = 6;
        dy = -Math.abs(dy); // luôn đi lên

        // Vị trí chạm (0 trái, 1 phải)
        double hitPos = (x - paddle.getX()) / paddle.getWidth();
        // Góc nảy tối đa ±75°
        double angle = Math.toRadians(150 * hitPos - 75);

        dx = speed * Math.cos(angle);
        dy = -speed * Math.sin(angle);
    }

    public boolean checkCollision(GameObject obj) {
        return x + radius > obj.getX() &&
                x - radius < obj.getX() + obj.getWidth() &&
                y + radius > obj.getY() &&
                y - radius < obj.getY() + obj.getHeight();
    }
}
