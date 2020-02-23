package sound.frame;

public class FormatConverter {

    public static int get16bitNumber(byte data[], int offset, boolean bigEndian){
        //The left shift and then right shift ensures that we get the proper twos complement of the data
        if(bigEndian){
            int result = (0xFF & data[offset]) << 24;
            result += (0xFF & data[offset + 1])  << 16;
            return result >> 16;
        } else {
            int result = (0xFF & data[offset + 1]) << 24;
            result |= (0xFF & data[offset])  << 16;
            return result >> 16;
        }
    }

    public static byte[] makeBitsFrom16bitNumber(int number, boolean bigEndian){
        if(bigEndian) {
            return new byte[]{(byte) (number >> 8), (byte) number};
        } else {
            return new byte[]{(byte) number, (byte) (number >> 8)};
        }
    }

    public static int getSignedInt(byte data[], int offset, boolean bigEndian){
        if(bigEndian){
            return (0xFF & data[offset]) << 24 | (0xFF & data[offset + 1]) << 16 | (0xFF & data[offset + 2]) << 8 | (0xFF & data[offset + 3]);
        } else {
            return (0xFF & data[offset + 3]) << 24 | (0xFF & data[offset + 2]) << 16 | (0xFF & data[offset + 1]) << 8 | (0xFF & data[offset]);
        }
    }

    public static byte[] signedIntToBytes(int number, boolean bigEndian){
        if(bigEndian) {
            return new byte[]{(byte) (number >> 24), (byte) (number >> 16), (byte) (number >> 8), (byte) number};
        } else {
            return new byte[]{(byte) number, (byte) (number >> 8), (byte) (number >> 16), (byte) (number >> 24) };
        }
    }


    public void flipEndianessOf16bit(byte[] bytes){
        for(int i = 0; i < bytes.length / 2; i++){
            byte tmp = bytes[i];
            bytes[i] = bytes[i+1];
            bytes[i + 1] = tmp;
        }
    }
}
