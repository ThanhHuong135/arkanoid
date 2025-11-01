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

    private BrickType type;

    private boolean breaking = false;       // trạng thái hiệu ứng vỡ
    private List<BrickParticle> particles = new ArrayList<>();

    public Brick(BrickType type,double x, double y, double width, double height) {
        super(x, y, width, height);
        this.type = type;
        this.hitPoints = type.getHitPoint();
        this.color = type.getColor();

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

    public BrickType getType() {
        return type;
    }

    public void setType(BrickType type) {
        this.type = type;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void takeHit() {
        hitPoints--;
        if (!breaking && hitPoints == 0) {
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
