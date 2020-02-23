package sound.buffer;

public class LinkedListNode {
    private byte[] data;
    volatile LinkedListNode next = null;

    public LinkedListNode(byte[] data){
        if(data == null){
            throw new NullPointerException("Null data passed");
        }
        this.data = data;
    }

    public LinkedListNode next(){
        return next;
    }

    public byte[] getData(){
        return data;
    }
}
