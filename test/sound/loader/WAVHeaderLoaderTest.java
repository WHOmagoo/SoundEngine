package sound.loader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.function.Executable;
import sound.BytePlayer;
import sound.Clip;
import sound.TimeSignature;
import sound.loader.exceptions.BadHeaderException;

import javax.sound.sampled.*;
import javax.xml.transform.Source;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WAVHeaderLoaderTest implements LifecycleMethodExecutionExceptionHandler {
    @Test
    void WAVHeaderLoaderConstructs() {
        try {
            FileInputStream input = new FileInputStream("70s Funk Clav_1.wav");
            Assertions.assertDoesNotThrow(() -> WAVHeaderLoader.WAVLoader(input));
        } catch (FileNotFoundException e) {
            fail(e);
        }
    }

    @Test
    void TestAudioFormatReadsCorrectly(){
        try {
            final String FILE_NAME = "70s Funk Clav_1.wav";
            FileInputStream input = new FileInputStream(FILE_NAME);

            AudioData audioData = WAVHeaderLoader.WAVLoader(input);

            javax.sound.sampled.Clip c = AudioSystem.getClip();

            c.open(AudioSystem.getAudioInputStream(new File(FILE_NAME)));


            boolean b = c.getFormat().equals(audioData.getFormat());
            Assertions.assertTrue(c.getFormat().matches(audioData.getFormat()));
//            Clip c = new Clip(audioData, new TimeSignature(4,4,120,44100, 12));
//
//            BytePlayer player = new BytePlayer(AudioSystem.getSourceDataLine(audioData.getFormat()));
//
//            for(int i = 0; i < c.getSize(); i++){
//                player.addToPlay(c.getFrame(i).getBytes());
//            }
//
//            player.start();

        } catch (BadHeaderException e) {
            fail("Header was not successfully loaded");
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            fail("General Error");
        }
    }

    @Test
    void TestAudioPlays(){
        try {
            final String FILE_NAME = "70s Funk Clav_1.wav";
            FileInputStream input = new FileInputStream(FILE_NAME);

            AudioData audioData = WAVHeaderLoader.WAVLoader(input);

            Clip c = new Clip(audioData, new TimeSignature(4,4,120,44100, 12));

            SourceDataLine d = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioData.getFormat()));
            BytePlayer player = new BytePlayer(d);

            for(int i = 0; i < c.getSize(); i++){
                player.addToPlay(c.getFrame(i).getBytes());
            }

            player.start();

        } catch (BadHeaderException e) {
            fail("Header was not successfully loaded");
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
            fail("General Error");
        }
    }

}