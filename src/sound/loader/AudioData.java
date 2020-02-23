package sound.loader;

import javax.sound.sampled.AudioFormat;

public class AudioData {
    AudioFormat format;
    byte data[];

    public AudioData(AudioFormat format, byte[] data) {
        this.format = format;
        this.data = data;
    }

    public byte[] getData(){
        return data;
    }

    public AudioFormat getFormat(){
        return format;
    }
}
