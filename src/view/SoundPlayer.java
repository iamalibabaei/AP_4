package view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import models.objects.Entity;

public class SoundPlayer {
    private static SoundPlayer soundPlayer = new SoundPlayer();
    private MediaPlayer backgroundMediaPlayer;
    private MediaPlayer mediaPlayer;

    private SoundPlayer() {
    }

    public static SoundPlayer getInstance() {
        return soundPlayer;
    }


    public void playBackground(String sound) {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
        }
        backgroundMediaPlayer = new MediaPlayer(new Media(sound));
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();
    }

    public void play(String sound) {
        mediaPlayer = new MediaPlayer(new Media(sound));
        mediaPlayer.play();
    }

    public static void play(Entity entity) {

    }
}
