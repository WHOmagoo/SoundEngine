package sound.frame;

import javax.sound.sampled.AudioFormat;

public abstract class SoundFrame {
    public abstract SoundFrame combine(SoundFrame otherSoundFrame) throws Exception;
}
