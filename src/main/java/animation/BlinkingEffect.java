package animation;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import object.Ball;
import object.Paddle;

public class BlinkingEffect {

    private Timeline currentBlink;
    private PauseTransition delay;
    private PauseTransition end;

    public void stop() {
        if (currentBlink != null) currentBlink.stop();
        if (delay != null) delay.stop();
        if (end != null) end.stop();
    }

    public void applyBlinkingEffect(Object target, Color activeColor, Color originalColor,
                                    double duration, double blinkDuration) {

        // Dừng hiệu ứng cũ nếu có
        stop();

        // Bắt đầu màu hiệu ứng
        setColor(target, activeColor);

        // Tạo nhấp nháy ở giai đoạn cuối
        double interval = 0.25; // mỗi 0.25 giây đổi màu
        int totalBlinkFrames = (int) (blinkDuration / interval);
        currentBlink = new Timeline();

        for (int i = 0; i < totalBlinkFrames; i++) {
            int frame = i;
            currentBlink.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(frame * interval), e -> {
                        if (frame % 2 == 0) setColor(target, activeColor);
                        else setColor(target, originalColor);
                    })
            );
        }

        delay = new PauseTransition(Duration.seconds(duration - blinkDuration));
        delay.setOnFinished(e -> currentBlink.play());

        end = new PauseTransition(Duration.seconds(duration));
        end.setOnFinished(e -> {
            setColor(target, originalColor);
            stop(); // cleanup
        });

        delay.play();
        end.play();

    }

    private void setColor(Object target, Color color) {
        if (target instanceof Ball ball) {
            ball.setColor(color);
        }
    }
}
