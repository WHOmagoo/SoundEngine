package gui;

import composition.Scheduler;
import composition.Song;
import sound.song.SongPartInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClipView extends JButton implements Updateable{
    Song song;
    Scheduler scheduler;
    SongPartInterface clip;
    boolean enabled = false;
    int prevMeasure = -1;

    public ClipView(Song song, Scheduler scheduler, SongPartInterface clip) {
        this.song = song;
        this.clip = clip;
        this.scheduler = scheduler;
        setText(clip.getName());
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduler.toggleNextMeasure(clip);
                setBackground(Color.ORANGE);
            }
        });

        song.addToUpdate(this);

        setPreferredSize(new Dimension(160,90));


    }

    @Override
    public void update(Object sender) {
        if(sender instanceof Song && scheduler.getMeasure() != prevMeasure){
            enabled = ((Song) sender).isPlaying(clip);

            if(enabled){
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.PINK);
            }
        }
    }
}
