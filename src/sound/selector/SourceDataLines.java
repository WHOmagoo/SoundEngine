package sound.selector;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class SourceDataLines {

    public static SourceDataLine getSourceDataLines(AudioFormat format, Line.Info info) throws LineUnavailableException {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();

        ArrayList<SourceDataLine> compatible = new ArrayList<>();
        for(Mixer.Info cur : infos){
            Mixer m = AudioSystem.getMixer(cur);
            if(m.isLineSupported(info)){
                compatible.add(AudioSystem.getSourceDataLine(format, m.getMixerInfo()));
            }
        }

        return compatible.get(1);
    }
}
