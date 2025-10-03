package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
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
