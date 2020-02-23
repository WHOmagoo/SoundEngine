package composition;

import sound.TimeSignature;
import sound.song.Pair;
import sound.song.SongPartInterface;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<SongPartInterface> toAdd;
    private ArrayList<SongPartInterface> toRemove;
    private volatile ArrayList<SongPartInterface> toToggle;
    private Song s;
    private TimeSignature t;
    int curMeasure = 0;
    int curSubFrame = -1;

    public Scheduler(Song s){
        toToggle = new ArrayList<>();
        toAdd = new ArrayList<>();
        toRemove = new ArrayList<>();
        this.s = s;
        s.setScheduler(this);
        t = s.getTimeSignature();
    }

//    public void scheduleRemove(SongPartInterface part, Integer frame){
//        toRemove.add(new Pair<>(part, frame));
//    }
//
//    public void scheduleAdd(SongPartInterface part, Integer frame){
//        toAdd.add(new Pair<>(part, frame));
//    }

    public void nextSubFrame(){
        curSubFrame++;
        if(curSubFrame % t.getSubBeatsPerMeasure() == 0){
            //new measure
            onNewMeasure();
        }
    }

    private void onNewMeasure(){
        curMeasure++;

        if(curMeasure % 4 == 0) {
            for (SongPartInterface spi : toToggle) {
                s.toggle(spi);
            }

            toToggle = new ArrayList<>();
        }
    }

    public synchronized void toggleNextMeasure(SongPartInterface toToggle){
        if(this.toToggle.contains(toToggle)){
            this.toToggle.remove(toToggle);
        } else {
            this.toToggle.add(toToggle);
        }

    }

    public int getMeasure() {
        return curMeasure;
    }
}
