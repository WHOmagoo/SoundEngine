package sound.loader;

import sound.Clip;
import sound.TimeSignature;
import sound.loader.exceptions.BadHeaderException;
import sound.song.Clips;

import javax.sound.sampled.AudioFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SongLoader {

    public static Clips loadClips(String[] fileNames, TimeSignature timeSignature){
        ArrayList<Clip> result = new ArrayList<>();

        AudioFormat format = null;

        for(String fileName : fileNames){
            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                AudioData curData = WAVHeaderLoader.WAVLoader(fileIn);

                if(format == null){
                    format = curData.getFormat();
                } else if(!format.matches(curData.getFormat())){
                    System.out.println("Format doesn't match for file " + fileName);
                    continue;
                }

                Clip c = new Clip(curData, timeSignature);
                result.add(c);
            } catch (FileNotFoundException e) {
                System.out.println("Missing file " + fileName);
            } catch (BadHeaderException | IOException e) {
                e.printStackTrace();
            }
        }

        return new Clips(result, format);
    }
}
