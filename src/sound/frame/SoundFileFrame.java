package sound.frame;

import sound.BytePlayer;

public abstract class SoundFileFrame {
    public abstract SoundFileFrame add(SoundFileFrame otherSoundFrame);
    public abstract byte[] getBytes();
}
