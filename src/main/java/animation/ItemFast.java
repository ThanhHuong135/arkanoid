package animation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Paddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemFast  {
    private List<double[]> explosion = new ArrayList<>();


    public void fast() {
        // Mỗi lần gọi tạo 1 hiệu ứng viền xanh nhấp nháy
        double lifetime = 500; // khoảng 1 giây (60 frame)
        // Đặt flag RGB = (0,1,1) để renderExplosion() nhận biết là viền
        explosion.add(new double[]{0, 0, 0, 0, lifetime, 0, 1, 1});
    }

    public void render(GraphicsContext gc, double x, double y, double width, double height) {
        for (Iterator<double[]> it = explosion.iterator(); it.hasNext();) {
            double[] p = it.next();

            // chỉ vẽ nếu là hiệu ứng viền
            if (p.length >= 8 && p[5] == 0 && p[6] == 1 && p[7] == 1) {
                p[4]--;
                if (p[4] <= 0) { it.remove(); continue; }

                double alpha = 0.6 + 0.4 * Math.sin(System.currentTimeMillis() * 0.02);
                gc.setGlobalAlpha(alpha);
                gc.setStroke(Color.CYAN);
                gc.setLineWidth(4);
                gc.strokeRoundRect(x - 3, y - 3, width + 6, height + 6, 25, 25);
                gc.setGlobalAlpha(1.0);
            }
        }
    }

}
