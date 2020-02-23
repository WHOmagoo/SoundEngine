import sound.BytePlayer;
import sound.Clip;
import sound.TimeSignature;
import composition.loader.ATXParser;
import sound.selector.SourceDataLines;
import sound.song.Clips;
import sound.song.PartialLoopingClipPart;
import sound.song.Song;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LineUnavailableException {
        if(args.length < 1){
            System.exit(1);
        }

        String fileRead;
        FileInputStream fis = new FileInputStream(args[0]);
        fileRead = new String(fis.readAllBytes());
        Clips clips = ATXParser.ParseATX(fileRead);

        TimeSignature t = new TimeSignature(4,4,120, 44100, 12);

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, clips.getFormat());

        Song s = new Song(t);
        BytePlayer bp = new BytePlayer(SourceDataLines.getSourceDataLines(clips.getFormat(), info), s);
        s.setPlayer(bp);


        int i = 0;
        for(Clip c : clips.getClips()){
            PartialLoopingClipPart clipPart = new PartialLoopingClipPart(t.getFramesPreceeding(t.getBeatsPerMeasure() * t.getSubBeatsPerBeat() * i), String.valueOf(i), c, 0, 12 * 4 * 4, 0);
            s.addClip(clipPart);
        }

        s.start();
    }
}
