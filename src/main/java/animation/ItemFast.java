package animation;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import object.Paddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemFast  {
    private List<double[]> explosion = new ArrayList<>();


    public void fast(String type) {
        double lifetime = 600; // thời gian tồn tại hiệu ứng (frame)

        switch (type) {
            case "FAST_BALL": // xanh dương
                explosion.add(new double[]{0, 0, 0, 0, lifetime, 0, 1, 1}); // RGB = (0,1,1)
                break;

            case "BIG_PADDLE": // xanh lá
                explosion.add(new double[]{0, 0, 0, 0, lifetime, 0, 1, 0}); // RGB = (0,1,0)
                break;

            case "SMALL_PADDLE": // đỏ
                explosion.add(new double[]{0, 0, 0, 0, lifetime, 1, 0, 0}); // RGB = (1,0,0)
                break;

            default:
                // Mặc định là trắng (nếu có loại khác)
                explosion.add(new double[]{0, 0, 0, 0, lifetime, 1, 1, 1});
                break;
        }
    }


    public void render(GraphicsContext gc, double x, double y, double width, double height) {
        for (Iterator<double[]> it = explosion.iterator(); it.hasNext();) {
            double[] p = it.next();

            p[4]--;
            if (p[4] <= 0) {
                it.remove();
                continue;
            }

            double alpha = 0.6 + 0.4 * Math.sin(System.currentTimeMillis() * 0.02);
            gc.setGlobalAlpha(alpha);

            // Lấy màu RGB từ mảng (p[5], p[6], p[7])
            gc.setStroke(Color.color(p[5], p[6], p[7]));
            gc.setLineWidth(4);
            gc.strokeRoundRect(x - 3, y - 3, width + 6, height + 6, 25, 25);

            gc.setGlobalAlpha(1.0);
        }
    }


}
