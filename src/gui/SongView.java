package gui;

import composition.Scheduler;
import composition.Song;
import composition.loader.ATXParser;
import sound.BytePlayer;
import sound.Clip;
import sound.TimeSignature;
import sound.selector.SourceDataLines;
import sound.song.Clips;
import sound.song.PartialLoopingClipPart;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class SongView {
    public static void main(String[] args) throws IOException, LineUnavailableException {
        JFrame frame = new JFrame("HelloWorldSwing");

        frame.setSize(1920,1080);
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        FlowLayout layout = new FlowLayout();
        layout.setHgap(30);
        layout.setVgap(20);

        frame.setLayout(layout);

        Clips clips = getClips(args);
        TimeSignature t = new TimeSignature(4,4,120, 44100, 12);

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, clips.getFormat());

        Song s = new Song(t);
        BytePlayer bp = new BytePlayer(SourceDataLines.getSourceDataLines(clips.getFormat(), info), s);
        s.setPlayer(bp);

        Scheduler scheduler = new Scheduler(s);

        int i = 0;
        for(Clip c : clips.getClips()){
            PartialLoopingClipPart clipPart = new PartialLoopingClipPart(0, c.getName(), c, 0, 12 * 4 * 4, 0);
            ClipView view = new ClipView(s, scheduler, clipPart);
            frame.add(view);
        }

        SchedulerView sv = new SchedulerView(scheduler);

        s.addToUpdate(sv);

        frame.add(sv);

        frame.add(new PlayButton(s));

        frame.setVisible(true);


    }

    public static Clips getClips(String[] args) throws IOException, LineUnavailableException {
        if(args.length < 1){
            System.exit(1);
        }

        String fileRead;
        FileInputStream fis = new FileInputStream(args[0]);
        fileRead = new String(fis.readAllBytes());
        Clips clips = ATXParser.ParseATX(fileRead);
        return clips;
    }
}
