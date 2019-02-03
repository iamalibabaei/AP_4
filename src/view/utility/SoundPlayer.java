package view.utility;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import models.objects.Entity;

public class SoundPlayer {
    private static SoundPlayer soundPlayer = new SoundPlayer();
    private MediaPlayer backgroundMediaPlayer;
    private double mediaVolume;
    private String playedSoundAddress;

    public String getCurrentPlayingSound()
    {
        return playedSoundAddress;
    }

    private SoundPlayer() {
        mediaVolume = 1.0;
    }

    public static SoundPlayer getInstance() {
        return soundPlayer;
    }


    public void playBackground(String sound) {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
        }
        playedSoundAddress = sound;
        backgroundMediaPlayer = new MediaPlayer(new Media(sound));
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();
    }

    public void play(String sound) {
        playedSoundAddress = sound;
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(sound));
        mediaPlayer.setVolume(mediaVolume);
        mediaPlayer.play();
    }

    public void setBackgroundSoundVolume(double value) {
        backgroundMediaPlayer.setVolume(value);
    }


    public void setMediaVolume(double mediaVolume) {
        this.mediaVolume = mediaVolume;
    }
}
