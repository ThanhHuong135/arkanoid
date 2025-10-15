package object;

import animation.BrickParticle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Brick extends GameObject {

    private int hitPoints;
    private Color color;

    private boolean breaking = false;       // trạng thái hiệu ứng vỡ
    private List<BrickParticle> particles = new ArrayList<>();

    public Brick(double x, double y, double width, double height, int hitPoints, Color color) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.color = color;
    }

    @Override
    public void update() {
        if (breaking) {
            // Cập nhật các particle
            Iterator<BrickParticle> it = particles.iterator();
            while (it.hasNext()) {
                BrickParticle p = it.next();
                p.update();
                if (!p.isAlive()) {
                    it.remove();
                }
            }
            // Nếu hết particle thì brick bị phá hủy thật sự
            if (particles.isEmpty()) {
                hitPoints = 0;
                breaking = false;
            }
        }
    }

    public void render(GraphicsContext gc) {
        if (!isDestroyed() && !breaking) {
            // Vẽ gạch bình thường khi chưa bị phá hủy hoặc chưa bắt đầu vỡ
            gc.setFill(color);
            gc.fillRoundRect(x, y, width, height, 10, 10);
            gc.setStroke(Color.BLACK);
            gc.strokeRoundRect(x, y, width, height, 10, 10);
        }

        if (breaking) {
            // Khi đang vỡ, chỉ vẽ các particle
            for (BrickParticle p : particles) {
                p.render(gc);
            }
        }
    }

    public void takeHit() {
        if (!breaking && hitPoints > 0) {
            breaking = true;
            generateParticles();
        }
    }

    private void generateParticles() {
        double centerX = x + width / 2;
        double centerY = y + height / 2;
        for (int i = 0; i < 10; i++) {
            particles.add(new BrickParticle(centerX, centerY, color));
        }
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
}
