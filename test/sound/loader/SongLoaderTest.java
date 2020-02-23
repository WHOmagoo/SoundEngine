package sound.loader;

import composition.loader.ATXParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sound.TimeSignature;
import sound.song.Clips;

import java.io.FileInputStream;
import java.io.IOException;

class SongLoaderTest {

    @Test
    void testLoadClips(){
        String[] input = new String[]{"70s Funk Clav_1.wav", "Classic Electric Piano_1.wav"};
        Assertions.assertEquals(2, SongLoader.loadClips(input, new TimeSignature(4, 4, 120, 441000, 12)).getSize());
    }

    @Test
    void testParseATX() throws IOException {
        FileInputStream file = new FileInputStream("testSong.atx");

        String s = new String(file.readAllBytes());

        Clips clip = ATXParser.ParseATX(s);

        Assertions.assertEquals(2, clip.getSize());
    }

}