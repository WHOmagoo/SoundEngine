package sound.frame;

//This is a default sound frame 44100kHz, 16 bit signed
public class DefaultFrame extends SoundFrame{
    byte data[];
    int frameNumber;

    DefaultFrame(byte data[], int frameNumber){
        this.data = data;
        this.frameNumber = frameNumber;
    }

    private DefaultFrame(int result){

    }

    @Override
    public SoundFrame combine(SoundFrame otherSoundFrame) throws Exception {
        throw new Exception("Combination not added for other sound frame formats");
    }

    public SoundFrame combine(DefaultFrame otherFrame){
        int result = 0;

        return new DefaultFrame(result);
    }
}
