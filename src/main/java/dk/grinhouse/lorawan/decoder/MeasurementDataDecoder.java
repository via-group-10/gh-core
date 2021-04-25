package dk.grinhouse.lorawan.decoder;

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
}
