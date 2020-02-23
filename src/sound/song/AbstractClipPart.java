package sound.song;

import sound.Clip;

public abstract class AbstractClipPart implements SongPartInterface {
    //length in subBeats
    private String name;
    Clip clip;
    int startingFrame;

    public AbstractClipPart(int startingFrame, String name, Clip clip){
        this.startingFrame = startingFrame;
        this.name = name;
        this.clip = clip;

    }

    int getDistanceFromStart(int frameNumber){
        return frameNumber - startingFrame;
    }

    public String getName(){
        return name;
    }
}
