package level;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.Ball;
import object.Paddle;
import object.Brick;

import java.util.List;
import java.util.ArrayList;
public class LevelOne extends BaseLevel {
    boolean kiemtra = false;

    private boolean completed = false;


    public LevelOne(double width, double height) {
        super(width, height);
    }


    @Override
    public void init() {}

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override

    public void update() {

    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
