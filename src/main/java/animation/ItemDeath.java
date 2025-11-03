package animation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ItemDeath {

    private List<double[]> explosion = new ArrayList<>();

    public void death(double cx, double cy) {
        Random r = new Random();
        for (int i = 0; i < 160; i++) {
            double angle = r.nextDouble() * 2 * Math.PI;
            double speed = r.nextDouble() +0.3;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            explosion.add(new double[]{cx, cy, vx, vy, 180 + r.nextInt(60)}); // lifetime 60-120 frame ~1-2s
        }
    }

    // update + render
    public void render(GraphicsContext gc) {
        for (Iterator<double[]> it = explosion.iterator(); it.hasNext(); ) {
            double[] p = it.next();
            p[0] += p[2]; // x
            p[1] += p[3]; // y
            p[4]--;       // lifetime
            gc.setGlobalAlpha(p[4] / 120.0);
            gc.setFill(Color.ORANGE);
            gc.fillOval(p[0], p[1], 10, 10);
            gc.setGlobalAlpha(1.0);
            if (p[4] <= 0) it.remove();
        }
    }
}
