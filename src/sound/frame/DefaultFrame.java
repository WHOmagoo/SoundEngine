package sound.frame;

import sound.BytePlayer;

//This is a default sound frame 44100kHz, 16 bit signed
public class DefaultFrame extends SoundFileFrame{
    int channel1;
    int channel2;

    private static final int BYTES_PER_FRAME = 4;

    public DefaultFrame(byte data[], int frameNumber){
        if(frameNumber * BYTES_PER_FRAME + 1 < data.length) {
            //if the frame is in bounds for data
            channel1 = FormatConverter.get16bitNumber(data, frameNumber * BYTES_PER_FRAME, false);
            channel2 = FormatConverter.get16bitNumber(data, frameNumber * BYTES_PER_FRAME + 2, false);
        } else {
            channel1 = 0;
            channel2 = 0;
        }

    }

    public DefaultFrame(int channel1, int channel2){
        this.channel1 = channel1;
        this.channel2 = channel2;
    }

    public int getChannel1(){
        return channel1;
    }

    public int getChannel2(){
        return channel2;
    }

    @Override
    public SoundFileFrame add(SoundFileFrame otherSoundFrame) {
        if(otherSoundFrame instanceof DefaultFrame){
            return add((DefaultFrame) otherSoundFrame);
        } else if(otherSoundFrame == null) {
            //TODO should we deep copy this?
            return this;
        } else {
            return null;
        }
        //throw new Exception("Combination not added for other sound frame formats");
    }

    @Override
    public SoundFileFrame remove(SoundFileFrame otherSoundFrame) {
        if(otherSoundFrame instanceof DefaultFrame){
            return remove((DefaultFrame) otherSoundFrame);
        } else if(otherSoundFrame == null) {
            //TODO should we deep copy this?
            return this;
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        channel1 = 0;
        channel2 = 0;
    }

    private SoundFileFrame remove(DefaultFrame otherSoundFrame){
        return new DefaultFrame(channel1 - otherSoundFrame.channel1, channel2 - otherSoundFrame.channel2);
    }

    private SoundFileFrame add(DefaultFrame otherFrame){
        return new DefaultFrame(channel1 + otherFrame.channel1, channel2 + otherFrame.channel2);
    }

    @Override
    public byte[] getBytes(){
        byte[] chan1 = FormatConverter.makeBitsFrom16bitNumber(channel1, false);
        byte[] chan2 = FormatConverter.makeBitsFrom16bitNumber(channel2, false);
        byte[] result = new byte[4];
        System.arraycopy(chan1, 0, result, 0, 2);
        System.arraycopy(chan2, 0, result, 2, 2);
        return result;
    }
}
