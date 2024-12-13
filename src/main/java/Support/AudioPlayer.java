package Support;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.logging.Logger;

public class AudioPlayer {

    private static final Logger logger = Logger.getLogger(AudioPlayer.class.getName());

    public static void playSound(String filePath){
        File file = new File(filePath);

        if (!file.exists()){
            return;
        }

        if (!file.getPath().endsWith(".wav")){
            return;
        }
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e){
            logger.info("AudioPlayer:playSound - Cant play sound -> " + filePath);
        }
    }
}
