package sound.frame;

import sound.BytePlayer;

public abstract class SoundFileFrame {
    public abstract SoundFileFrame add(SoundFileFrame otherSoundFrame);
    public abstract SoundFileFrame remove(SoundFileFrame frame);

    public abstract void clear();

    public abstract byte[] getBytes();
}
