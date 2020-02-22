package sound;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class TimeSignatureTest {

    @Test
    void getFramesPerNoteNoRemainder(){
        TimeSignature ts;

        ts = new TimeSignature(4, 4, 220500, 44100, 12);
        Assertions.assertEquals(1, ts.getFramesCount(1));

        ts = new TimeSignature(4, 4, 220500 / 2, 44100, 12);
        Assertions.assertEquals(2, ts.getFramesCount(1));

        ts = new TimeSignature(4, 4, 220500 / 10, 44100, 12);
        Assertions.assertEquals(10, ts.getFramesCount(1));

        ts = new TimeSignature(4, 4, 220500 / 10, 44100, 12);
        Assertions.assertEquals(20, ts.getFramesCount(2));

        ts = new TimeSignature(4,4, 144, 44100, 12);
        Assertions.assertEquals(1531, ts.getFramesCount(1));
    }

    @Test
    void getFramesPerNoteWithRemainderNoRounding() {
        TimeSignature ts;
        ts = new TimeSignature(4, 4, 144, 44100, 12);

        assertEquals(1531 * 4 + 1, ts.getFramesCount(4));
        assertEquals(1531 * 8 + 2, ts.getFramesCount(8));
        assertEquals(1531*12 + 3, ts.getFramesCount(12));


    }

    @Test
    void getFramesPerNoteWithRemainderAndRounding(){
        TimeSignature ts;
        ts = new TimeSignature(4, 4, 144, 44100, 12);

        //Rounded
        assertEquals(1531 * 2, ts.getFramesCount(2), 1);
        assertEquals(1531 * 3 + 1, ts.getFramesCount(3));
    }
}