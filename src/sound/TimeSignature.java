package sound;

import java.math.BigDecimal;
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

    private int framesPerSubdivision;
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
        BigDecimal results[] = framesPerMinute.divideAndRemainder(subBeatsPerMinute);

        framesPerSubdivision = results[0].intValue();
        frameRemainder = results[1].intValue();
    }

    public int getFramesCount(int subBeatsCount){
        return framesPerSubdivision * subBeatsCount + new BigDecimal(frameRemainder * subBeatsCount).divide (subBeatsPerMinute, RoundingMode.HALF_EVEN).intValue();
    }
}
