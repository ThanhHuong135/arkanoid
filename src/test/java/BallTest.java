package object;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    @Test
    void testCornerCollision() {
        Ball ball = new Ball(49, 49, 10, 5, 1, 1);
        Brick brick = new Brick(55, 55, 20, 10, 2, "normal");

        assertTrue(ball.checkCollision(brick), "Ball phải phát hiện va chạm ở góc brick");
        double oldDx = ball.getDx();
        double oldDy = ball.getDy();
        ball.bounceOff(brick);
        assertTrue(ball.getDx() == -oldDx || ball.getDy() == -oldDy,
                "Sau va chạm góc, dx hoặc dy phải đảo chiều");
    }

    @Test
    void testMultipleHitsReduceHitPoints() {
        Ball ball = new Ball(50, 50, 10, 5, 1, 1);
        Brick brick = new Brick(50, 50, 20, 10, 2, "normal");

        assertEquals(2, brick.getHitPoints());
        ball.bounceOff(brick);
        brick.hit();
        assertEquals(1, brick.getHitPoints(), "Brick hitPoints phải giảm sau 1 lần va chạm");
        ball.bounceOff(brick);
        brick.hit();
        assertTrue(brick.isDestroyed(), "Brick phải bị phá hủy sau đủ số hitPoints");
    }

    @Test
    void testUnbreakableBrick() {
        Ball ball = new Ball(100, 100, 10, 5, 1, 1);
        Brick brick = new Brick(100, 100, 20, 10, Integer.MAX_VALUE, "unbreakable");

        int oldHitPoints = brick.getHitPoints();
        brick.hit();
        assertEquals(oldHitPoints, brick.getHitPoints(), "Brick unbreakable không giảm hitPoints");
    }

    @Test
    void testBallVelocityAfterObliqueCollision() {
        Ball ball = new Ball(50, 50, 10, 5, 3, 4);
        Brick brick = new Brick(55, 52, 20, 10, 2, "normal");

        double oldDx = ball.getDx();
        double oldDy = ball.getDy();
        ball.bounceOff(brick);
        // Sau va chạm xiên, ít nhất 1 trong 2 vận tốc phải đổi dấu
        assertTrue(ball.getDx() == -oldDx || ball.getDy() == -oldDy,
                "Sau va chạm xiên, dx hoặc dy phải đảo chiều");
    }
}
