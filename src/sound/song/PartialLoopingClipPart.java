package sound.song;

import sound.Clip;
import sound.frame.SubBeatFrame;

public class PartialLoopingClipPart extends AbstractClipPart {
    int clipStartingFrame;
    int clipLength;
    int loopCount;

    public PartialLoopingClipPart(int songStartingFrame, String name, Clip clip, int clipStartingFrame, int clipLength, int loopCount) {
        super(songStartingFrame, name, clip);
        this.clipStartingFrame = clipStartingFrame;
        this.clipLength = clipLength;
        this. loopCount = loopCount;
    }

    @Override
    public SubBeatFrame getFrame(int frameNumber){
        int distanceFromStart = getDistanceFromStart(frameNumber);

        if(distanceFromStart < 0){
            return SubBeatFrame.NONE;
        } else {
            return clip.getFrame((distanceFromStart + clipStartingFrame) % clipLength - clipStartingFrame);
        }
    }
}
