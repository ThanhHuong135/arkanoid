package manager;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import object.Paddle;

public class InputManager {
    public static void attach(Scene scene, Paddle paddle) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT -> paddle.setGoLeft(true);
                    case RIGHT -> paddle.setGoRight(true);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT -> paddle.setGoLeft(false);
                    case RIGHT -> paddle.setGoRight(false);
                }
            }
        });
    }
}
