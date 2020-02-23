package sound.song;

import sound.frame.SubBeatFrame;

public interface SongPartInterface {
    SubBeatFrame getFrame(int frameNumber);
    String getName();
}
