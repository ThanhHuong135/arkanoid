package animation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BrickParticle {
    private double x, y;
    private double dx, dy;
    private double size;
    private Color color;
    private long createTime;
    private long lifespan = 300; // milliseconds

    public BrickParticle(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = 3 + Math.random() * 3;
        this.dx = (Math.random() - 0.5) * 4;
        this.dy = (Math.random() - 0.5) * 4;
        this.createTime = System.currentTimeMillis();
    }

    public void update() {
        x += dx;
        y += dy;
        dy += 0.1; // gravity effect
    }

    public boolean isAlive() {
        return System.currentTimeMillis() - createTime < lifespan;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x, y, size, size);
    }
}
