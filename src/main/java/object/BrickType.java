package object;

import javafx.scene.paint.Color;

public enum BrickType {
    NORMAL('N', 1, Color.web("#ff6b6b")),
    MEDIUM('M', 2, Color.web("#4ecdc4")),
    HARD('H', 3, Color.web("#ffe66d")),
    EMPTY('.', 0, Color.web("#ffffff00"));

    private final char code;
    private final int hitPoint;
    private final Color color;

    BrickType(char code, int hitPoint, Color color) {
        this.code = code;
        this.hitPoint = hitPoint;
        this.color = color;
    }

    public char getCode() {
        return code;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Color getColor() {
        return color;
    }

    public static BrickType fromCode(char code) {
        for(BrickType type : BrickType.values()) {
            if(type.code == code) {
                return type;
            }
        }
        return EMPTY;
    }
}
