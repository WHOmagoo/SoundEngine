package sound.song;

import sound.Clip;

import javax.sound.sampled.AudioFormat;
import java.util.List;

public class Clips {
    AudioFormat format;
    List<Clip> clips;


    public Clips(List<Clip> clips, AudioFormat format){
        this.format = format;
        this.clips = clips;
    }

    public List<Clip> getClips() {
        return clips;
    }

    public AudioFormat getFormat(){
        return format;
    }

    public int getSize(){
        return clips == null ? 0 : clips.size();
    }
}
