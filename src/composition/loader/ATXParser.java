package composition.loader;

import sound.Clip;
import sound.TimeSignature;
import sound.loader.SongLoader;
import sound.song.Clips;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATXParser {
    public static Clips ParseATX(String s){
        String[] lines = s.split(System.lineSeparator());

        Pattern p = Pattern.compile("^\"(.*)\".*");

        ArrayList<String> filePaths = new ArrayList<>();
        for(String line : lines){
            Matcher m = p.matcher(line);
            if(m.matches()) {
                filePaths.add(m.group(1));
            }
        }

        return SongLoader.loadClips(filePaths.toArray(String[]::new), new TimeSignature(4,4,120,44100, 12));
    }
}
