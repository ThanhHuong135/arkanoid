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
        double speed = 3.5;

        // Tính vị trí chạm (0 = mép trái, 1 = mép phải)
        double hitPos = (x - paddle.getX()) / paddle.getWidth();
        hitPos = Math.max(0, Math.min(1, hitPos));

        // Góc nảy từ -60° (trái) đến +60° (phải)
        double minAngle = -60;
        double maxAngle = 60;
        double angle = Math.toRadians(minAngle + (maxAngle - minAngle) * hitPos);

        // Tính tâm của bóng và paddle
        double ballCenterX = x + radius;
        double ballCenterY = y + radius;
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2.0;
        double paddleCenterY = paddle.getY() + paddle.getHeight() / 2.0;

        double dxFromCenter = ballCenterX - paddleCenterX;
        double dyFromCenter = ballCenterY - paddleCenterY;

        // Nếu va từ bên trái/phải → đổi hướng ngang
        if (Math.abs(dxFromCenter) > Math.abs(dyFromCenter)) {
            dx = (dxFromCenter > 0) ? Math.abs(dx) : -Math.abs(dx);
            dy = Math.abs(speed * Math.sin(angle)); // đi xuống để không "leo" lên paddle
            return;
        }

        // Nếu va từ dưới paddle → bật xuống
        if (dyFromCenter > 0) {
            dy = Math.abs(speed); // đi xuống
            return;
        }

        // Nếu va từ trên (trường hợp bình thường)
        double minVertical = Math.toRadians(30);
        if (Math.abs(angle) < minVertical) {
            angle = (angle < 0 ? -minVertical : minVertical);
        }

        dx = speed * Math.cos(angle);
        dy = -Math.abs(speed * Math.sin(angle)); //*

    }


        public boolean checkCollision(GameObject obj) {
        double closestX = Math.max(obj.getX(), Math.min(x, obj.getX() + obj.getWidth()));
        double closestY = Math.max(obj.getY(), Math.min(y, obj.getY() + obj.getHeight()));
        double dx = x - closestX;
        double dy = y - closestY;
        return (dx * dx + dy * dy) <= (radius * radius);
    }
}
