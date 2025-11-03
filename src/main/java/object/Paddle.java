package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Paddle extends MovableObject {

    private Image paddleImage;

    private boolean goLeft, goRight;

    private List<double[]> explosion = new ArrayList<>();

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
        super(x, y, 120, 60);
        loadImage();
    }

    public double getWidth() {
        return super.getWidth();
    }

    public void setWidth(double newWidth) {
        this.width = newWidth;
    }

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        loadImage();
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

    private void loadImage() {
        // Đường dẫn tương đối trong thư mục resources (nếu dùng Maven/Gradle)
        paddleImage = new Image(getClass().getResourceAsStream("/assets/paddle/paddle.png"));
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(paddleImage, x, y, width, height);
    }

}
