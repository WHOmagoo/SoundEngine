package composition;

import gui.ClipView;
import gui.Updateable;
import sound.BytePlayer;
import sound.TimeSignature;
import sound.frame.DefaultFrame;
import sound.frame.SoundFileFrame;
import sound.frame.SubBeatFrame;
import sound.song.SongPartInterface;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.nanoTime;

public class Song {
    TimeSignature timeSignature;
    int currentSubBeat;
    int currentMeasure;
    BytePlayer player;
    SoundFileFrame[] soundFrames;
    private HashMap<SongPartInterface, Boolean> playing;
    private Scheduler scheduler;

    private ArrayList<Updateable> toUpdate;
    private boolean shouldPause = false;

    public Song(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
        playing = new HashMap<>();
        soundFrames = new SoundFileFrame[timeSignature.getFramesCountForFrameNumber(0)+1];
        for(int i = 0; i < soundFrames.length; i++){
            soundFrames[i] = new DefaultFrame(0,0);
        }

        toUpdate = new ArrayList<>();
    }

    public void setPlayer(BytePlayer newPlayer){
        player = newPlayer;
    }

    public boolean start(){
        try {
            if(player != null) {
                player.open();

                currentSubBeat = 0;
                for(int i = 0; i < 4; i++) {
                    //Add four frames for buffer
                    onNextSubBeat();
                    currentSubBeat++;
                }


                new Thread(() -> player.start()).start();
                return true;
            } else {
                return false;
            }
        } catch (LineUnavailableException e) {
            return false;
        }
    }

    public TimeSignature getTimeSignature(){
        return timeSignature;
    }

    public void MusicPlayed(int frameRate, int frameCount){

    }

    public void addClip(SongPartInterface clip){
        if (!playing.containsKey(clip)){
            playing.put(clip, false);
        }
    }

    public void removeClip(SongPartInterface clip){
        if (playing.containsKey(clip)){
            playing.remove(clip, false);
        }
    }

    private void setCurrentMeasure(int newMeasure){
        if(newMeasure != currentMeasure) {
            currentMeasure = newMeasure;
            onNextMeasure();
        }
    }

    private void onNextMeasure() {
        for(Updateable updateable : toUpdate){
            updateable.update(this);
        }
//        while(!toAdd.isEmpty()) {
//            SongPartInterface willAdd = toAdd.remove(0);
//            System.out.println("Adding " + willAdd.getName());
//            playing.put(willAdd, true);
//        }
//
//        while(!toAdd.isEmpty()){
//            SongPartInterface willAdd = toAdd.remove(0);
//            System.out.println("Adding " + willAdd.getName());
//            playing.put(willAdd, true);
//        }
    }

    public void nextSubBeat(){
        if(!shouldPause) {
            currentSubBeat++;
            setCurrentMeasure(currentSubBeat / timeSignature.getSubBeatsPerMeasure());
            onNextSubBeat();
        }
//
//        new Thread(this::onNextSubBeat).start();
    }

    private void onNextSubBeat(){
        long start = nanoTime();

        if(scheduler != null) {
            scheduler.nextSubFrame();
        }

//        SoundFileFrame[] soundFrames = new SoundFileFrame[timeSignature.getFramesCountForFrameNumber(currentSubBeat)];
        for(int i = 0; i < soundFrames.length; i++){
            soundFrames[i].clear();
        }

        SubBeatFrame sbFrame = new SubBeatFrame(soundFrames);

        long middle = nanoTime();
//        System.out.println("First  " + (middle - start));

        int i = 0;
        for (SongPartInterface part : playing.keySet()) {
            sbFrame.add(part.getFrame(currentSubBeat));
            i++;

        }

        long middle2 = System.nanoTime();
//        System.out.println("Second " + (middle2 - middle));
        if(player != null) {
            player.addToPlay(sbFrame.getBytes());
//            player.addToPlay(playing.entrySet().iterator().next().getKey().getFrame(currentSubBeat).getBytes());

        }


        long finish = nanoTime();
//        System.out.println("Finish " + (finish - middle2) + "\n");

    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public boolean isPlaying(SongPartInterface spi) {
        return playing.containsKey(spi);
    }

    public void toggle(SongPartInterface spi){
        if(isPlaying(spi)){
            playing.remove(spi);
        } else {
            playing.put(spi, true);
        }
    }

    public void addToUpdate(Updateable updateable) {
        this.toUpdate.add(updateable);
    }

    public void stop() {
        shouldPause = true;
    }
}
