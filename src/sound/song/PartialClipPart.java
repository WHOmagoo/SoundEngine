package sound.song;

import sound.Clip;
import sound.frame.SubBeatFrame;

public class PartialClipPart extends AbstractClipPart {
    int startingFrame;
    int framesToPlay;

    public PartialClipPart(int songStartingFrame, String name, Clip clip, int clipStartingFrame, int framesToPlay) {
        super(songStartingFrame, name, clip);
        this.startingFrame = clipStartingFrame;
        this.framesToPlay = framesToPlay;
    }

    @Override
    public SubBeatFrame getFrame(int frameNumber){
        int framesFromStartOfClip = getDistanceFromStart(frameNumber);
        if(framesFromStartOfClip >= 0 && framesFromStartOfClip < framesToPlay) {
            return clip.getFrame(this.startingFrame + framesFromStartOfClip);
        } else {
            return SubBeatFrame.NONE;
        }
    }
}
