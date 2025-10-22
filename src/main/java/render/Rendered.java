package game.render;

import javafx.scene.canvas.GraphicsContext;

public abstract class Rendered {
    protected double x, y; // vị trí
    protected double width, height;

    public Rendered(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // phương thức vẽ, các class con override
    public abstract void draw(GraphicsContext gc);
}
