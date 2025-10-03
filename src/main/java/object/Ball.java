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

    public void render(GraphicsContext gc) {
        gc.setFill(Color.AQUA);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void update() {
        move(); // di chuyển bóng
    }

    public void bounceOff(GameObject other) {
        if (x + radius > other.getX() && x - radius < other.getX() + other.getWidth()) {
            dy *= -1;
        } else {
            dx *= -1;
        }
    }

    public boolean checkCollision(GameObject other) {
        return x + radius > other.getX() &&
                x - radius < other.getX() + other.getWidth() &&
                y + radius > other.getY() &&
                y - radius < other.getY() + other.getHeight();
    }
}
