package sound.loader;

import sound.TimeSignature;
import sound.frame.DefaultFrame;
import sound.frame.SoundFileFrame;
import sound.frame.SubBeatFrame;

public class FrameMaker {

    public static SubBeatFrame[] makeFrames(AudioData data, TimeSignature timeSignature, int framesCount){
        int frameSize;
        SubBeatFrame result[] = new SubBeatFrame[timeSignature.getFramesCountForFrameNumber(framesCount)];
        int i = 0;
        for(i = 0; i < result.length; i++){
            result[i] = makeSubBeatFrame(data, timeSignature, i);
        }

        return result;
    }

    public static SubBeatFrame makeSubBeatFrame(AudioData data, TimeSignature timeSignature, int frameNumber){
        int preceedingFramesCount = timeSignature.getFramesPreceeding(frameNumber);
        SoundFileFrame[] frames = new SoundFileFrame[timeSignature.getFramesCountForFrameNumber(frameNumber)];

        for(int i = 0; i < frames.length; i++){
            frames[i] = makeSoundFileFrame(data, preceedingFramesCount + i);
        }

        return new SubBeatFrame(frames);

    }

    public static SoundFileFrame makeSoundFileFrame(AudioData data, int frameNumber){
        return new DefaultFrame(data.data, frameNumber);
    }
}
