package dk.grinhouse.lorawan.messages;

public class UplinkMessage
{
     private String cmd;
     private String EUI;
     private long ts;
     private boolean ack;
     private int port;
     private String encdata;
     private String data;
}
