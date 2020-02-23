package sound;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * This class will subdivided each beat into 12 subBeats
 * This will allow for accuracy for eighth note triplets and 16th notes within a single beat
 */
public class TimeSignature {

    private int beatsPerMeasure;
    private int lengthOfBeat;
    private int subBeatsPerBeat;
    private int frameRate;

    private BigDecimal framesPerSubdivision;
    private int frameRemainder;
    private int beatsPerMinute;

    BigDecimal framesPerMinute;
    BigDecimal subBeatsPerMinute;

    public TimeSignature(int beatsPerMeasure, int lengthOfBeat, int beatsPerMinute, int frameRate, int subBeatsPerBeat) {
        this.beatsPerMeasure = beatsPerMeasure;
        this.lengthOfBeat = lengthOfBeat;
        this.beatsPerMinute = beatsPerMinute;
        this.frameRate = frameRate;

        framesPerMinute = new BigDecimal(frameRate * 60);
        setSubBeatsPerBeat(subBeatsPerBeat);
    }

    public void setSubBeatsPerBeat(int newSubBeatsPerBeat){
        subBeatsPerBeat = newSubBeatsPerBeat;

        subBeatsPerMinute = new BigDecimal(beatsPerMinute * subBeatsPerBeat);
        framesPerSubdivision = framesPerMinute.divide(subBeatsPerMinute, RoundingMode.DOWN);

//        framesPerSubdivision = results[0;
//        frameRemainder = results[1].intValue();
    }

    //Counts sound file frames for the given number of subBeats
    public int getFramesPreceeding(int subBeatFrameNumber){
//        int result = framesPerSubdivision * subBeatFrameNumber;
//        if((subBeatFrameNumber - 1) % subBeatsPerMinute.intValue() < frameRemainder){
//            result++;
//        }
//        BigDecimal extra = new BigDecimal(frameRemainder * subBeatFrameNumber).divide (subBeatsPerMinute, RoundingMode.HALF_EVEN);
//        return result;
        return framesPerSubdivision.multiply(new BigDecimal(subBeatFrameNumber)).round(MathContext.DECIMAL32).intValue();
    }

    public int getFramesCountForFrameNumber(int subBeatFrameNumber){
        return getFramesPreceeding(subBeatFrameNumber + 1) - getFramesPreceeding(subBeatFrameNumber);
    }

    //Trims dangling data that doesn't fit in a frame
    public int getSubBeatsCount(int framesCount) {
        BigDecimal result = subBeatsPerMinute.multiply(new BigDecimal(framesCount)).divide(framesPerMinute, RoundingMode.DOWN);
        return result.intValue();
    }
}
