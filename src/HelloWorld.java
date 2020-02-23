import sound.BytePlayer;
import sound.Clip;
import sound.TimeSignature;
import sound.frame.SubBeatFrame;
import sound.loader.AudioData;
import sound.loader.WAVHeaderLoader;
import sound.loader.exceptions.BadHeaderException;
import sound.selector.SourceDataLines;

import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) throws IOException, BadHeaderException, LineUnavailableException {
        final String FILE_NAME = "70s Funk Clav_1.wav";
        FileInputStream input = new FileInputStream(FILE_NAME);

        AudioData audioData = WAVHeaderLoader.WAVLoader(input);

        TimeSignature timeSignature = new TimeSignature(4,4,320,44100, 12);
        Clip c = new Clip(audioData, timeSignature);
        Clip c2 = new Clip(WAVHeaderLoader.WAVLoader(new FileInputStream("Classic Electric Piano_1.wav")), timeSignature);

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioData.getFormat());
        SourceDataLine line = SourceDataLines.getSourceDataLines(audioData.getFormat(), info);

        BytePlayer player = new BytePlayer(line, null);



        int framesWritten = 0;
        for(int i = 0; i < 2; i++){
            SubBeatFrame frame = c.getFrame(i);
            framesWritten += frame.getFrames().length;
            player.addToPlay(frame.getBytes());
        }


//        player.addToPlay(buff);

        c.getSoundFramesCount();
        int subBeats = timeSignature.getSubBeatsCount(753170);

        player.open();
        Thread t = new Thread(player::start);
        t.start();
        for(int i = 2; i < c.getSoundFramesCount(); i++){
            long start = System.nanoTime();

//            c.getFrame(i).add(c2.getFrame(i));
//            c.getFrame(i).remove(c2.getFrame(i));
            c.getFrame(i).remove(c2.getFrame(i));

            player.addToPlay(c.getFrame(i).getBytes());
            long finish = System.nanoTime();

            if(finish - start  >= 22675 * .5 * 2756) {
                System.out.println("Frame " + i + " took " + (finish - start));
            }
        }
    }
}
