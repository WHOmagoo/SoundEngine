package gui;

import composition.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayButton extends JButton {
    Song song;
    boolean isPlaying = false;
    public PlayButton(Song s){
        song = s;
        setPreferredSize(new Dimension(160, 90));
        setText("Play / Pause");
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isPlaying){
                    s.stop();
                } else {
                    s.start();
                }
            }
        });
    }
}
