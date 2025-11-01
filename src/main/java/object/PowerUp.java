package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Paddle;

public class PowerUp {
    private double x, y;
    private double width = 40;
    private double height = 40;
    private double speed = 5;
    private boolean active = true;
    private String type;

    public PowerUp(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update() {
        y += speed;
    }

    public void render(GraphicsContext gc) {
        if (!active) return;

        Color color;
        switch (type) {
            case "FAST_BALL" -> color = Color.DEEPSKYBLUE;
            case "BIG_PADDLE" -> color = Color.ORANGE;
            case "SLOW_BALL" -> color = Color.LIGHTGREEN;
            case "SMALL_PADDLE" -> color = Color.YELLOW;
            case "DEATH" -> color = Color.DARKRED;
            default -> color = Color.WHITE;
        }

        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }

    public boolean checkCollision(Paddle paddle) {
        return active &&
                x + width > paddle.getX() &&
                x < paddle.getX() + paddle.getWidth() &&
                y + height > paddle.getY() &&
                y < paddle.getY() + paddle.getHeight();
    }

    public String getType() {
        return type;
    }

    public double getY(){
        return y;
    }


    public void setWidth(double width) {
        this.width = width;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

}
