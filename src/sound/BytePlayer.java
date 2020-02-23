package sound;

import sound.buffer.LinkedList;
import sound.buffer.LinkedListNode;
import composition.Song;

import javax.sound.sampled.*;

/**
 * This class will put bytes into an audio stream for them to be played
 */
public class BytePlayer {
    SourceDataLine output;
    volatile LinkedList toPlay;
    Song toNotify;


    //Counts the number of 32nd notes since the beginning of the song;
    int notePostion;


    public BytePlayer(SourceDataLine dataLine, Song toNotify){
        output = dataLine;
        this.toNotify = toNotify;
        toPlay = new LinkedList();
        output.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                System.out.println("Line LIstener");
                System.out.println(event);
            }
        });
    }

    public AudioFormat getFormat(){
        return output.getFormat();
    }

    public void open() throws LineUnavailableException {
        output.open();
        int bytesCount = output.available();
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

            cur = toPlay.next();
            songFrameCount++;
        }

        System.out.printf("Finished, played %d song frames, %d sound frames. Min buffered %d, max buffered %d\n", songFrameCount, soundFrameCount, minLength, maxLength);
    }

    private void play(byte[] bytes, int offset, int count){
        //locks thread
        output.write(bytes, offset, count);
        if(toNotify != null){
            toNotify.nextSubBeat();
        }
    }

    public synchronized void addToPlay(byte[] newBytes){
        toPlay.add(newBytes);
    }
}
