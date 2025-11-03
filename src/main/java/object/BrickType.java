package object;

import javafx.scene.image.Image;

public enum BrickType {
    NORMAL('N', 1, new String[]{
            "/assets/brick/normal-brick/normal.png"
    }),
    MEDIUM('M', 2, new String[]{
            "/assets/brick/medium-brick/medium-brick-2.png",
            "/assets/brick/medium-brick/medium-brick-1.png"
    }),
    HARD('H', 3, new String[]{
            "/assets/brick/hard-brick/hard-brick-3.png",
            "/assets/brick/hard-brick/hard-brick-2.png",
            "/assets/brick/hard-brick/hard-brick-1.png"
    });

    private final char code;
    private final int hitPoint;
    private final Image[] images;

    BrickType(char code, int hitPoint, String[] imagePaths) {
        this.code = code;
        this.hitPoint = hitPoint;
        this.images = new Image[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            // Load ảnh từ resources, không còn lỗi NullPointerException
            images[i] = new Image(BrickType.class.getResource(imagePaths[i]).toExternalForm());
        }
    }

    public char getCode() {
        return code;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Image[] getImages() {
        return images;
    }

    public static BrickType fromCode(char code) {
        for (BrickType type : BrickType.values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
