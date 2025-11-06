package manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    private static MediaPlayer backgroundPlayer;
    private static double volume = 0.5;
    private static boolean muted = false;

    public static void playHitSound() {
        play("/assets/sound/hit.mp3");
    }

    public static void playGameOverSound() {
        play("/assets/sound/game_over.mp3");
    }

    private static void play(String path) {
        if (muted) return; // tắt tiếng thì không phát
        MediaPlayer player = new MediaPlayer(new Media(SoundManager.class.getResource(path).toString()));
        player.setVolume(volume);
        player.play();
    }

    public static void setVolume(double v) {
        volume = Math.max(0, Math.min(1, v)); // giữ trong [0,1]
    }

    public static double getVolume() {
        return volume;
    }

    public static void setMute(boolean m) {
        muted = m;
    }

    public static boolean isMuted() {
        return muted;
    }
}
