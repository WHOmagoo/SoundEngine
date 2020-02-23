package sound;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class TimeSignatureTest {

    @Test
    void getFramesPerNoteNoRemainder(){
        TimeSignature ts;

        ts = new TimeSignature(4, 4, 220500, 44100, 12);
        Assertions.assertEquals(1, ts.getFramesPreceeding(1));

        ts = new TimeSignature(4, 4, 220500 / 2, 44100, 12);
        Assertions.assertEquals(2, ts.getFramesPreceeding(1));

        ts = new TimeSignature(4, 4, 220500 / 10, 44100, 12);
        Assertions.assertEquals(10, ts.getFramesPreceeding(1));

        ts = new TimeSignature(4, 4, 220500 / 10, 44100, 12);
        Assertions.assertEquals(20, ts.getFramesPreceeding(2));

        ts = new TimeSignature(4,4, 144, 44100, 12);
        Assertions.assertEquals(1531, ts.getFramesPreceeding(1));
    }

    @Test
    void getFramesPerNoteWithRemainderNoRounding() {
        TimeSignature ts;
        ts = new TimeSignature(4, 4, 144, 44100, 12);

        assertEquals(1531 * 4, ts.getFramesPreceeding(4));
        assertEquals(1531 * 8, ts.getFramesPreceeding(8));
        assertEquals(1531*12, ts.getFramesPreceeding(12));


    }

    @Test
    void getFramesPerNoteWithRemainderAndRounding(){
        TimeSignature ts;
        ts = new TimeSignature(4, 4, 144, 44100, 12);

        //Rounded
        assertEquals(1531 * 2, ts.getFramesPreceeding(2), 1);
        assertEquals(1531 * 3, ts.getFramesPreceeding(3));
    }

    @Test
    void BigDecimalTest(){
        BigDecimal top = new BigDecimal(2646000);
        BigDecimal bottom = new BigDecimal(1440);
        BigDecimal result = top.divide(bottom, RoundingMode.DOWN);

        Assertions.assertEquals(1837, result.intValueExact());
    }
}