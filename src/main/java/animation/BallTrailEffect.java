package animation;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class BallTrailEffect {
    private static class TrailPoint {
        Point2D position;
        double alpha;
        double size;

        TrailPoint(Point2D position, double size) {
            this.position = position;
            this.alpha = 1.0;
            this.size = size;
        }
    }

    private LinkedList<TrailPoint> trailPoints = new LinkedList<>();
    private int maxLength;
    private double radius;

    private int frameCounter = 0;
    private double frameInterval = 1; // Cứ sau 1 frame mới thêm 1 điểm → vệt thưa hơn

    public BallTrailEffect(int maxLength, double radius) {
        this.maxLength = maxLength;
        this.radius = radius;
    }

    public void addPosition(double x, double y) {
        frameCounter++;
        if (frameCounter >= frameInterval) {
            frameCounter = 0;

            // Thêm điểm với kích thước ban đầu (gấp đôi bán kính)
            trailPoints.add(new TrailPoint(new Point2D(x, y), radius * 2));

            if (trailPoints.size() > maxLength) {
                trailPoints.removeFirst();
            }
        }
    }

    public void update() {
        for (TrailPoint point : trailPoints) {
            point.alpha *= 0.75;   // Giảm độ trong suốt
            point.size *= 0.9;     // Giảm kích thước
        }

        // Xóa nếu quá mờ
        trailPoints.removeIf(p -> p.alpha < 0.05);
    }

    public void render(GraphicsContext gc) {
        gc.save();
        for (TrailPoint point : trailPoints) {
            gc.setFill(Color.AQUA.deriveColor(0, 1, 1, point.alpha));
            double halfSize = point.size / 2;
            gc.fillOval(
                    point.position.getX() - halfSize,
                    point.position.getY() - halfSize,
                    point.size,
                    point.size
            );
        }
        gc.restore();
    }

    public void clear() {
        trailPoints.clear();
    }
}
