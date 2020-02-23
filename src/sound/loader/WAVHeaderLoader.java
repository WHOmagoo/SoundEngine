package sound.loader;

import sound.frame.FormatConverter;
import sound.loader.exceptions.BadHeaderException;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;

public class WAVHeaderLoader {
    private static final String TYPE_NAME = "WAV";

    public static AudioData WAVLoader(InputStream stream) throws IOException, BadHeaderException {
        byte buff[];
        buff = stream.readNBytes(12);
        String chunkID = new String(buff, 0, 4);

        boolean bigEndian;
        if("RIFF".equals(chunkID)) {
            bigEndian = false;
        } else if("RFIX".equals(chunkID)) {
            bigEndian = true;
        } else {
            throw new BadHeaderException(TYPE_NAME, "Chunk ID is not RIFF or RIFX, it was " + chunkID);
        }

        int chunkSize = FormatConverter.getSignedInt(buff, 4, false);

        String fileFormat = new String(buff, 8, 4);
        if(!"WAVE".equals(fileFormat)){
            throw new BadHeaderException(TYPE_NAME, "File format is not WAVE, it was " + fileFormat);
        }

        String subchunkID;

        //Find fmt chunk
        do {
            buff = stream.readNBytes(8);
            subchunkID = new String(buff, 0, 4);
            int subchunkSize = FormatConverter.getSignedInt(buff, 4, false);
            //Buffer in the rest of the subchunk
            buff = stream.readNBytes(subchunkSize);
        } while(!"fmt ".equals(subchunkID));



        int format = FormatConverter.get16bitNumber(buff, 0, false);

        if(format != 1){
            //Not stored as PCM
            throw new BadHeaderException(TYPE_NAME, "Data not stored as PCM");
        }

        int numChannels = FormatConverter.get16bitNumber(buff, 2, false);

        int sampleRate = FormatConverter.getSignedInt(buff, 4, false);
        int byteRate = FormatConverter.getSignedInt(buff, 8, false);
        int blockAlign = FormatConverter.get16bitNumber(buff, 10, false);
        int bytesPerSample = FormatConverter.get16bitNumber(buff, 12, false);

        //Create audio format for this file
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, blockAlign * 8, numChannels, bytesPerSample, sampleRate, false);


        do {
            buff = stream.readNBytes(8);
            subchunkID = new String(buff, 0, 4);
            int subchunkSize = FormatConverter.getSignedInt(buff, 4, false);
            //Buffer in the rest of the subchunk
            buff = stream.readNBytes(subchunkSize);
        } while(!"data".equals(subchunkID));

        return new AudioData(audioFormat, buff);
    }
}
