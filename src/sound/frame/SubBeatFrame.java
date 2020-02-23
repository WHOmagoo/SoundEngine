package sound.frame;

import sound.BytePlayer;
import sound.TimeSignature;

/**
 * Each song frame operates on PCM data that is 2 channel 16bit in little endian format
 * A single SubBeatFrame represents a single sub beat
 */
public class SubBeatFrame {
    public static final SubBeatFrame NONE = new SubBeatFrame(new SoundFileFrame[]{});
    SoundFileFrame frames[];

    public SubBeatFrame(SoundFileFrame[] frames){
        this.frames = frames;
    }

    public SoundFileFrame[] getFrames(){
        return frames;
    }

    public byte[] getBytes(){
        byte[] buff= null;
        if(frames.length > 0) {
            int frameSizeInBytes = frames[0].getBytes().length;

            buff = new byte[frames.length * frames[0].getBytes().length];
            for (int frameIndex = 0; frameIndex < frames.length; frameIndex++) {
                byte[] tmpFrame = frames[frameIndex].getBytes();
                System.arraycopy(tmpFrame, 0, buff, frameIndex * frameSizeInBytes, tmpFrame.length);
            }
        }

        return buff;
    }

    public void add(SubBeatFrame other){
        if(other != NONE && this != NONE) {
            for (int i = 0; i < frames.length; i++) {
                if (i >= other.frames.length) {
                    break;
                }

                frames[i] = frames[i].add(other.frames[i]);
            }
        }
    }

    public void remove(SubBeatFrame other){
        for(int i = 0; i < frames.length; i++){
            if(i >= other.frames.length){
                break;
            }

            frames[i] = frames[i].remove(other.frames[i]);
        }
    }
}
