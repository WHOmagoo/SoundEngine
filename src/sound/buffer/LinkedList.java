package sound.buffer;

import java.util.Collection;
import java.util.List;

public class LinkedList {
    volatile LinkedListNode start;
    volatile LinkedListNode end;
    volatile LinkedListNode current;

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
        return start;
    }

    public synchronized LinkedListNode next(){
        current = current.next;
        return current;
    }
}
