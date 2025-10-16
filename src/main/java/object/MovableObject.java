package object;

public abstract class MovableObject extends GameObject {
    protected double dx, dy;  // tốc độ di chuyển theo trục x, y

    public MovableObject(double x, double y, double width,  double height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(double x, double y, double width,  double height) {
        super(x, y, width, height);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double setDx(double Dx) {
        return this.dx = Dx;
    }

    public double setDy(double Dy) {
        return this.dy = Dy;
    }
}
