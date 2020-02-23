package sound.song;

import sound.Clip;
import sound.frame.SubBeatFrame;

public class DefaultClipPart extends AbstractClipPart {


    public DefaultClipPart(int startingFrame, String name, Clip clip){
        super(startingFrame, name, clip);
    }

    @Override
    public SubBeatFrame getFrame(int frameNumber){
        return clip.getFrame(getDistanceFromStart(frameNumber));
    }
}
