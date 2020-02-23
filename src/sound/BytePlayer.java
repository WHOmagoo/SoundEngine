package sound;

import sound.buffer.LinkedList;
import sound.buffer.LinkedListNode;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;

/**
 * This class will put bytes into an audio stream for them to be played
 */
public class BytePlayer {
    SourceDataLine output;
    ArrayList<Notifyable> toNotify;
    LinkedList toPlay;


    //Counts the number of 32nd notes since the beginning of the song;
    int notePostion;


    public BytePlayer(SourceDataLine dataLine){
        output = dataLine;
        toNotify = new ArrayList<>();
        toPlay = new LinkedList();
    }

    public AudioFormat getFormat(){
        return output.getFormat();
    }

    public void open() throws LineUnavailableException {
        output.open();
    }

    public void start(){
        output.start();
        LinkedListNode cur = toPlay.getStart();
        int songFrameCount = 0;
        int soundFrameCount = 0;

        int minLength = cur.getData().length;
        int maxLength = cur.getData().length;
        while(cur != null){

//            for(int i = 0; i < 1200; i++) {
//                play(cur.getData(), i * 300 * 4 + i * 100, 300 * 4);
//            }
            play(cur.getData(), 0, cur.getData().length);

            if(minLength > cur.getData().length){
                minLength = cur.getData().length;
            }


            if(maxLength < cur.getData().length){
                maxLength = cur.getData().length;
            }
            soundFrameCount += cur.getData().length;

            cur = cur.next();
            songFrameCount++;
        }

        System.out.printf("Finished, played %d song frames, %d sound frames. Min buffered %d, max buffered %d\n", songFrameCount, soundFrameCount, minLength, maxLength);
    }

    private void play(byte[] bytes, int offset, int count){
        //locks thread
        output.write(bytes, offset, count);
    }

    public synchronized void addToPlay(byte[] newBytes){
        toPlay.add(newBytes);
    }
}
