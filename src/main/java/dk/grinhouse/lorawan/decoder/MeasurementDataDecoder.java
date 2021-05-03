package dk.grinhouse.lorawan.decoder;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class MeasurementDataDecoder
{
     /* Data sample: 001a001803bb */
     private String data;
     private byte[] dataAsBytes;

     public MeasurementDataDecoder(String data)
     {
          this.data = data;
          this.dataAsBytes = convertToBytes(data);
     }

     private byte[] convertToBytes(String data)
     {
          int byteArrayLength = data.length() % 2 == 0 ? data.length() / 2 : data.length() / 2 + 1;
          byte[] result = new byte[byteArrayLength];
          for (int i = 0; i < data.length(); i += 2) {
               result[i/2] = hexToByte(data.substring(i, i+2));
          }
          return result;
     }

     public static byte hexToByte(String hexString) {
          int firstDigit = toDigit(hexString.charAt(0));
          int secondDigit = toDigit(hexString.charAt(1));
          return (byte) ((firstDigit << 4) + secondDigit);
     }

     private static int toDigit(char hexChar) {
          int digit = Character.digit(hexChar, 16);
          if(digit == -1) {
               throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
          }
          return digit;
     }

     public byte[] getDataAsBytes()
     {
          return dataAsBytes;
     }

     public static String encodeThresholds(float minimumTemperature, float maximumTemperature, float minimumHumidity, float maximumHumidity, float minimumCarbonDioxide, float maximumCarbonDioxide)
     {
          byte[] data = new byte[12];
          data[0] = (byte)(((int) minimumTemperature) >> 8);
          data[1] = (byte)(((int) minimumTemperature));
          data[2] = (byte)(((int) maximumTemperature) >> 8);
          data[3] = (byte)(((int) maximumTemperature));
          data[4] = (byte)(((int) minimumHumidity) >> 8);
          data[5] = (byte)(((int) minimumHumidity));
          data[6] = (byte)(((int) maximumHumidity) >> 8);
          data[7] = (byte)(((int) maximumHumidity));
          data[8] = (byte)(((int) minimumCarbonDioxide) >> 8);
          data[9] = (byte)(((int) minimumCarbonDioxide));
          data[10] = (byte)(((int) maximumCarbonDioxide) >> 8);
          data[11] = (byte)(((int) maximumCarbonDioxide));
          return new HexBinaryAdapter().marshal(data);
     }
}
