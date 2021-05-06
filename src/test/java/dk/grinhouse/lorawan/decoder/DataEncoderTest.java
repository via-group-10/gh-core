package dk.grinhouse.lorawan.decoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataEncoderTest
{
     private final String DATA = "001a001803d8";
     private final byte[] DATA_BYTE = new byte[] { 0, 26, 0, 24, 3, -40 };

     @Test
     public void hexToByte() {
          byte[] result = DataEncoder.hexToBytes(DATA);

          byte[] expected = DATA_BYTE;

          assertArrayEquals(expected, result);
     }

     @Test
     public void bytesToHex() {
          String result = DataEncoder.bytesToHex(DATA_BYTE);

          String expected = DATA.toUpperCase();

          assertEquals(expected, result);
     }
}