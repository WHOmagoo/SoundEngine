package sound.song;

import sound.Clip;
import sound.frame.SubBeatFrame;

public class LoopingClipPart extends AbstractClipPart {

    public LoopingClipPart(int startingFrame, String name, Clip clip) {
        super(startingFrame, name, clip);
    }

    @Override
    public SubBeatFrame getFrame(int frameNumber) {
        return clip.getFrame(getDistanceFromStart(frameNumber) % clip.getSoundFramesCount());
    }
}
