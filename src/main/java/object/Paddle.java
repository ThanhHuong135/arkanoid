package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    double speed = 50;
    public void moveLeft() {
        x -= speed;
        if (x < 0) x = 0; // không ra ngoài trái
    }


    // Di chuyển phải
    public void moveRight(double sceneWidth) {
        x += speed;
        if (x + width > sceneWidth) x = sceneWidth - width; // không ra ngoài phải
    }

    @Override
    public void update() {
        //
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }
}
