package level;

import javafx.scene.canvas.GraphicsContext;

public abstract class BaseLevel {
    protected double width, height;

    public BaseLevel(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public abstract void init();
    public abstract void render(GraphicsContext gc);
    public abstract void update();
    public abstract boolean isCompleted();
}
