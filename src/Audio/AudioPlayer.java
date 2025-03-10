package Audio;

import javax.sound.sampled.*;

public class AudioPlayer {

    private Clip clip;
    private boolean isLooping;

    public AudioPlayer(String s) { 
        try {
            // Load the WAV file
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream(s)); // Ensure file path is correct

            clip = AudioSystem.getClip();
            clip.open(ais);

            isLooping = false; // Initialize to not loop by default

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Play audio, optionally looping
    public void play(boolean loop) {
        if (clip == null) {
            return;
        }

        // Stop any existing audio before starting new audio
        stop();

        clip.setFramePosition(0); // Reset the clip to start from the beginning

        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
        } else {
            clip.start(); // Play once without looping
        }
    }

    public void stop() {
        System.out.println("Stopping music...");
        if (clip.isRunning()) {
            clip.stop();
        }
    }
    
    public void close() {
        System.out.println("Closing music...");
        stop();
        if (clip != null) {
            clip.close();
        }
    }
    
    // Optional: Add a method to check if the music is playing
    public boolean isPlaying() {
        return clip.isRunning();
    }

    // Optional: Add a method to check if the music is looping
    public boolean isLooping() {
        return isLooping;
    }
}
