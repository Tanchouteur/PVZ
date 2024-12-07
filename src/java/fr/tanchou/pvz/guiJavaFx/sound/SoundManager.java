package fr.tanchou.pvz.guiJavaFx.sound;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundManager {
    private final Map<String, MediaPlayer> soundEffects;
    private MediaPlayer backgroundMusic;

    public SoundManager() {
        soundEffects = new HashMap<>();
    }

    public void loadSound(String name, String filePath) {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(filePath)).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        soundEffects.put(name, mediaPlayer);
    }

    public void playSound(String name) {
        MediaPlayer mediaPlayer = soundEffects.get(name);
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop any previous instance of the sound
            mediaPlayer.play();
        }
    }

    public void playBackgroundMusic(String filePath) {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
        Media music = new Media(Objects.requireNonNull(getClass().getResource(filePath)).toExternalForm());
        backgroundMusic = new MediaPlayer(music);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Loop the background music
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
}