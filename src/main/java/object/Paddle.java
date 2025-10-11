package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    private boolean goLeft, goRight;

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
        super(x, y, 120, 30);
    }

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
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

    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }
}
