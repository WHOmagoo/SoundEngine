import sound.BytePlayer;
import sound.Clip;
import sound.TimeSignature;
import sound.frame.SubBeatFrame;
import sound.loader.AudioData;
import sound.loader.WAVHeaderLoader;
import sound.loader.exceptions.BadHeaderException;
import sound.selector.SourceDataLines;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) throws IOException, BadHeaderException, LineUnavailableException {
        final String FILE_NAME = "70s Funk Clav_1.wav";
        FileInputStream input = new FileInputStream(FILE_NAME);

        AudioData audioData = WAVHeaderLoader.WAVLoader(input);

        TimeSignature timeSignature = new TimeSignature(4,4,120,44100, 12);
        Clip c = new Clip(audioData, timeSignature);


        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioData.getFormat());
        SourceDataLine line = SourceDataLines.getSourceDataLines(audioData.getFormat(), info);

        BytePlayer player = new BytePlayer(line);

        FileInputStream fis = new FileInputStream(new File("70s Funk Clav_1.wav"));
        byte[] buff = fis.readAllBytes();

        line.open(

        );


        int framesWritten = 0;
        for(int i = 0; i < c.getSize(); i++){
            SubBeatFrame frame = c.getFrame(i);
            framesWritten += frame.getFrames().length;
            player.addToPlay(frame.getBytes());
        }


//        player.addToPlay(buff);

        c.getSize();
        int subBeats = timeSignature.getSubBeatsCount(753170);
        player.open();
        player.start();
    }
}
