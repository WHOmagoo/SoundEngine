import gui.SongView;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LineUnavailableException {
        SongView.getClips(args);
    }
}
