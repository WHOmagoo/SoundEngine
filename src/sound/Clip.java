package sound;

import sound.frame.*;
import sound.loader.AudioData;

import javax.naming.Name;
import java.util.ArrayList;

public class Clip {
    private SubBeatFrame[] subBeatFrames;
    private String name = "NONE";

    private Clip(SubBeatFrame[] subBeatFrames, int subBeatFrameSize, int subBeatsCount){
        this.subBeatFrames = subBeatFrames;
    }

    public int getSoundFramesCount(){
        return subBeatFrames.length;
    }

//    public int getSoundFrameCount(){
//        return soundFrameCount;
//    }

    public Clip(AudioData audioData, TimeSignature signature, String name){
        this(audioData, signature);
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Clip(AudioData audioData, TimeSignature signature){
        SoundFileFrame[] fileFrames = new SoundFileFrame[audioData.getData().length / audioData.getFormat().getFrameSize()];
        for(int i = 0; i < fileFrames.length; i++){
            fileFrames[i] = FrameFactory.makeSoundFrame(audioData.getData(), i, audioData.getFormat());
            if(fileFrames[i] == null){
                System.out.println(i + " was null");
            }
        }

        System.out.printf("Data is %d bytes long. Clip size is %d frames\n", audioData.getData().length, fileFrames.length);

        subBeatFrames = new SubBeatFrame[signature.getSubBeatsCount(fileFrames.length)];

        int prevEnd = 0;

        for(int i = 0; i < subBeatFrames.length; i++){
            int frameSize = signature.getFramesCountForFrameNumber(i);
            int frameStart = signature.getFramesPreceeding(i);
            SoundFileFrame[] soundFileFrames = new SoundFileFrame[frameSize];
            System.arraycopy(fileFrames, frameStart, soundFileFrames, 0, soundFileFrames.length);
            subBeatFrames[i] = new SubBeatFrame(soundFileFrames);

            if(frameStart != prevEnd){
                System.out.println("Skipping bytes");
            }

            prevEnd = frameStart + frameSize;
        }
    }


    public SubBeatFrame getFrame(int frameNumber){
        if(frameNumber < subBeatFrames.length && frameNumber >= 0){
            return subBeatFrames[frameNumber];
        } else {
            return SubBeatFrame.NONE;
        }
    }


//    public byte[] toBytes(){
//        byte[] result = new byte[subBeatFrameSize * subBeatsCount * 4];
//        for(int i = 0; i < subBeatFrames.length; i++){
//
//            for(int j = 0; j < subBeatFrames[i].getFrames().length; j++) {
//                byte[] frameBytes = subBeatFrames[i].getFrames()[j].getBytes();
//                for(int copyIndex = 0; copyIndex < frameBytes.length; copyIndex++) {
//                    result[i * subBeatFrameSize + j + copyIndex] = frameBytes[copyIndex];
//                }
//            }
//        }
//
//        return result;
//    }

    public static Clip clipsCombiner(ArrayList<Clip> clips, int subBeatsCount, int subBeatFrameSize){

        SubBeatFrame[] beatFrames = new SubBeatFrame[subBeatsCount];

        for(int i = 0; i < beatFrames.length; i++){

            SoundFileFrame[] soundFrames = new DefaultFrame[subBeatFrameSize];

            for(int j = 0; j < soundFrames.length; j++){
                soundFrames[j] = new DefaultFrame(0,0);
            }

            beatFrames[i] = new SubBeatFrame(soundFrames);
        }

        for(int i = 0; i < subBeatsCount && !clips.isEmpty(); i++){
            ArrayList<Clip> toRemove = new ArrayList<>();
            for(Clip c : clips){
                for(int subBeat = 0; subBeat < subBeatsCount; subBeat++){
                    if(subBeat > c.subBeatFrames.length){
                        toRemove.add(c);
                        break;
                    }
                    beatFrames[i].getFrames()[subBeat].add(c.subBeatFrames[i].getFrames()[subBeat]);
                }
            }

            clips.removeAll(toRemove);
        }

        return new Clip(beatFrames, subBeatsCount, subBeatFrameSize);
    }
}
