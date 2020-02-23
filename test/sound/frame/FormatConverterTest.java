package sound.frame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class FormatConverterTest {

    @Test
    void get16bitNumberBigEndian() {

        Assertions.assertEquals(127 << 8, FormatConverter.get16bitNumber(new byte[]{127, 0x00}, 0, true));
        Assertions.assertEquals(0b0111111111111111, FormatConverter.get16bitNumber(new byte[]{(byte) 0b01111111, (byte) 0b11111111}, 0, true));
    }

    @Test
    void get16bitNumberLittleEndian() {

        Assertions.assertEquals(127, FormatConverter.get16bitNumber(new byte[]{127, 0x00}, 0, false));
        Assertions.assertEquals(0b0111111111111111, FormatConverter.get16bitNumber(new byte[]{(byte) 0b11111111, (byte) 0b01111111}, 0, false));
    }

    @Test
    void testByteConversion(){
        Assertions.assertEquals(-1, (byte) 0xFF);
        Assertions.assertEquals(0x12, (byte) 0xFF12);
    }

    @Test
    void makeBitsFrom16bitNumberLittleEndian(){
        Assertions.assertArrayEquals(new byte[]{0, 127}, FormatConverter.makeBitsFrom16bitNumber(127 << 8, false));
        Assertions.assertArrayEquals(new byte[]{(byte) 0b01111111, (byte) 0b11111111}, FormatConverter.makeBitsFrom16bitNumber(0b1111111101111111, false));
    }

    @Test
    void makeBitsFrom16bitNumberBigEndian() {
        Assertions.assertArrayEquals(new byte[]{127, 0x00}, FormatConverter.makeBitsFrom16bitNumber(127 << 8, true));
        Assertions.assertArrayEquals(new byte[]{(byte) 0b01111111, (byte) 0b11111111}, FormatConverter.makeBitsFrom16bitNumber(0b0111111111111111, true));
    }

    @Test
    void getSignedInt() {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(0x7F78E322);
        Assertions.assertEquals(0x7F78E322, FormatConverter.getSignedInt(b.array(), 0, true));

        Assertions.assertEquals(0x22E3787F, FormatConverter.getSignedInt(b.array(), 0, false));
    }

    @Test
    void signedIntToBytes() {
    }
}