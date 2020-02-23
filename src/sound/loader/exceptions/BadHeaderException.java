package sound.loader.exceptions;

public class BadHeaderException extends Exception {

    public BadHeaderException(String type, String extraMessage){
        super("Invalid header for " + type + System.lineSeparator() + extraMessage);
    }
}
