package dk.grinhouse.lorawan.decoder;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class DataEncoder
{
     /* Data sample: 001a001803bb */

     public static byte[] hexToBytes(String hexString)
     {
          int byteArrayLength = hexString.length() % 2 == 0 ? hexString.length() / 2 : hexString.length() / 2 + 1;
          byte[] result = new byte[byteArrayLength];
          for (int i = 0; i < hexString.length(); i += 2) {
               result[i/2] = hexToByte(hexString.substring(i, i+2));
          }
          return result;
     }

     private static byte hexToByte(String hexString) {
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

     public static String bytesToHex(byte[] bytes)
     {
          return new HexBinaryAdapter().marshal(bytes);
     }
}
