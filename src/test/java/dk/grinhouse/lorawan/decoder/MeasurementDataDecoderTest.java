package dk.grinhouse.lorawan.decoder;

import dk.grinhouse.lorawan.MeasurementBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MeasurementDataDecoderTest
{
     private MeasurementDataDecoder decoder;
     private final String DATA = "001a001803d8";

     @BeforeEach
     public void setup()
     {
          decoder = new MeasurementDataDecoder(DATA);
     }

     @Test
     public void charToByte() {
          var result = decoder.getDataAsBytes();

          byte[] expected = new byte[] { 0, 26, 0, 24, 3, -40 };

          assertArrayEquals(expected, result);
     }
}