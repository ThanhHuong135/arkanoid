package object;

import animation.BrickParticle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Brick extends GameObject {

    private int hitPoints;
    private int hitsTaken = 0;

    private Color color;

    private BrickType type;

    private boolean breaking = false;       // trạng thái hiệu ứng vỡ
    private List<BrickParticle> particles = new ArrayList<>();

    public Brick(BrickType type, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.type = type;
        this.hitPoints = type.getHitPoint();
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
            Image[] images = type.getImages();
            // chọn index hợp lệ
            int index = Math.max(0, Math.min(hitPoints - 1, images.length - 1));
            gc.drawImage(images[index], x, y, width, height);
        }

        if (breaking) {
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
        if (isDestroyed() || breaking) return; // nếu gạch đã chết thì không làm gì
        hitsTaken++;
        hitPoints--;
        // Chỉ tạo particle khi hitPoints giảm xuống 0
        if (hitPoints <= 0) {
            breaking = true;
            generateParticles();
        }
    }

    private void generateParticles() {
        double centerX = x + width / 2;
        double centerY = y + height / 2;

        Color particleColor;
        switch (type) {
            case NORMAL:
                particleColor = Color.web("#ffe66d"); // vàng
                break;
            case MEDIUM:
                particleColor = Color.web("#4ecdc4"); // xanh
                break;
            case HARD:
                particleColor = Color.web("#ff6b6b"); // đỏ
                break;
            default:
                particleColor = Color.WHITE;
        }

        for (int i = 0; i < 15; i++) {
            particles.add(new BrickParticle(centerX, centerY, particleColor));
        }
    }


    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
}
