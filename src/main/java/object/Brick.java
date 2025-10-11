package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GameObject {

    private int hitPoints;
    //private String type;
    private Color color;

    public Brick(double x, double y, double width, double height, int  hitPoints, Color color) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.color = color;
    }

    @Override
    public void update() {
        //
    }


    public void render(GraphicsContext gc) {
        if (!isDestroyed()) { // chỉ vẽ nếu chưa destroyed
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, width, height);
        }
    }

    public void takeHit() {
        if (hitPoints > 0) hitPoints--;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

}
