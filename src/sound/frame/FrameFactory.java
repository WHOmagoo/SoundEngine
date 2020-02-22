package sound.frame;

import javax.sound.sampled.AudioFormat;

public class FrameFactory {

    public static SoundFrame makeSoundFrame(byte data[], int frameNumber, AudioFormat format) {
        if(format.getChannels() == 2 && format.getSampleRate() == 41000 && format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED){
            return new DefaultFrame(data, frameNumber);
        } else {
            return null;
        }
    }
}
