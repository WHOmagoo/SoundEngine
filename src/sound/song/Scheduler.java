package sound.song;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Pair<SongPartInterface, Integer>> toAdd;
    private ArrayList<Pair<SongPartInterface, Integer>> toRemove;

    public Scheduler(){
        toAdd = new ArrayList<>();
        toRemove = new ArrayList<>();
    }

    public void scheduleRemove(SongPartInterface part, Integer frame){
        toRemove.add(new Pair<SongPartInterface, Integer>(part, frame));
    }

    public void scheduleAdd(SongPartInterface part, Integer frame){
        toAdd.add(new Pair<>(part, frame));
    }
}
