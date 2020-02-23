package sound.song;

public class Pair<T, T1> {
    T t;
    T1 t1;

    public Pair(T t, T1 t1){
        this.t = t;
        this.t1 = t1;
    }

    public T getFirst(){
        return t;
    }

    public T1 getSecond(){
        return t1;
    }
}
