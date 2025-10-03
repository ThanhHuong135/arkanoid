package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GameObject {

    private int hitPoints;
    private String type;

    public Brick(double x, double y, double width, double height, int  hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    @Override
    public void update() {
        //
    }


    public void render(GraphicsContext gc) {
    }

    public void takeHit() {
        if (hitPoints > 0) hitPoints--;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
}
