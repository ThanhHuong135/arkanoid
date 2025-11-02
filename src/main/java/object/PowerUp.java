package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Paddle;
import javafx.scene.image.Image;


public class PowerUp {
    private double x, y;
    private double speed = 2;
    private boolean active = true;
    private String type;

    private static final Image DEATH_IMAGE =
            new Image(PowerUp.class.getResource("/assets/item/wing.png").toExternalForm());
    private double widthDeath = 633 * 0.05;
    private double heightDeath = 1466 * 0.05;

    private static final Image FAST_IMAGE =
            new Image(PowerUp.class.getResource("/assets/item/thunder.png").toExternalForm());
    private double widthFast = 300 * 0.1;
    private double heightFast = 512 * 0.1;

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

        switch (type) {
            case "DEATH" ->
                    gc.drawImage(DEATH_IMAGE, x, y, widthDeath, heightDeath);

            case "FAST_BALL" ->
                    gc.drawImage(FAST_IMAGE, x, y, widthFast, heightFast);

            default -> {
                gc.setFill(Color.WHITE);
                gc.fillOval(x, y, widthFast, heightFast);
            }
        }
    }

    public boolean checkCollision(Paddle paddle) {
        if (!active) return false;

        double pw, ph;

        switch (type) {
            case "DEATH" -> {
                pw = widthDeath;
                ph = heightDeath;
            }
            case "FAST_BALL" -> {
                pw = widthFast;
                ph = heightFast;
            }
            default -> {
                pw = widthFast; // mặc định
                ph = heightFast;
            }
        }

        return x + pw > paddle.getX() &&
                x < paddle.getX() + paddle.getWidth() &&
                y + ph > paddle.getY() &&
                y < paddle.getY() + paddle.getHeight();
    }

    public String getType() {
        return type;
    }

    public double getX() {
        return  x;
    }

    public double getY(){
        return y;
    }

    public double getWidthDeath(){
        return widthDeath;
    }

    public double getHeightDeath(){
        return heightDeath;
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
