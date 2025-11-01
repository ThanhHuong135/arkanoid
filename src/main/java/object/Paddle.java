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

    public double getWidth() {
        return super.getWidth();
    }

    public void setWidth(double newWidth) {
        this.width = newWidth;
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

    public void increaseWidth(){
        this.width *= 1.1;
        if (this.width > 400) {
            this.width = 400;
        }
    }

    @Override
    public void update() {
        //
    }

    private Color color = Color.ORANGE;
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRoundRect(x, y, width, height, 15, 15);  // bo góc 15px
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
