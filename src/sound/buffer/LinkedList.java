package sound.buffer;

import java.util.Collection;
import java.util.List;

public class LinkedList {
    volatile LinkedListNode start;
    volatile LinkedListNode end;
    volatile LinkedListNode current;
    volatile int pos;

    public LinkedList(){
    }

    public synchronized void add(byte[] bytes){
        if(start == null) {
            start = new LinkedListNode(bytes);
            end = start;
        } else {
            end.next = new LinkedListNode(bytes);
            end = end.next;
        }
    }

    public LinkedListNode getStart(){
        current = start;
        return start;
    }

    public synchronized LinkedListNode next(){
        pos++;
        if(current != null) {
            current = current.next;
            return current;
        } else {
            return null;
        }
    }

    public int getPos() {
        return pos;
    }
}
