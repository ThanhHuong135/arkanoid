package manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    private static MediaPlayer backgroundPlayer;

    public static void playHitSound() {
        new MediaPlayer(new Media(SoundManager.class.getResource("/assets/sound/hit.mp3").toString())).play();
    }

    public static void playGameOverSound() {
        new MediaPlayer(new Media(SoundManager.class.getResource("/assets/sound/game_over.mp3").toString())).play();
    }

}
