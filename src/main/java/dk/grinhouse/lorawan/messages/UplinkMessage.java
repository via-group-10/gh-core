package dk.grinhouse.lorawan.messages;

public class UplinkMessage
{
     private String cmd;
     private String EUI;
     private long ts;
     private boolean ack;
     private int fcnt;
     private int port;
     private String data;
     private long freq;
     private String dr;
     private int rssi;
     private float snr;
     private transient String encdata;

     public UplinkMessage()
     {
     }

     public String getCmd()
     {
          return cmd;
     }

     public void setCmd(String cmd)
     {
          this.cmd = cmd;
     }

     public String getEUI()
     {
          return EUI;
     }

     public void setEUI(String EUI)
     {
          this.EUI = EUI;
     }

     public long getTs()
     {
          return ts;
     }

     public void setTs(long ts)
     {
          this.ts = ts;
     }

     public boolean isAck()
     {
          return ack;
     }

     public void setAck(boolean ack)
     {
          this.ack = ack;
     }

     public int getFcnt()
     {
          return fcnt;
     }

     public void setFcnt(int fcnt)
     {
          this.fcnt = fcnt;
     }

     public int getPort()
     {
          return port;
     }

     public void setPort(int port)
     {
          this.port = port;
     }

     public String getData()
     {
          return data;
     }

     public void setData(String data)
     {
          this.data = data;
     }

     public long getFreq()
     {
          return freq;
     }

     public void setFreq(long freq)
     {
          this.freq = freq;
     }

     public String getDr()
     {
          return dr;
     }

     public void setDr(String dr)
     {
          this.dr = dr;
     }

     public int getRssi()
     {
          return rssi;
     }

     public void setRssi(int rssi)
     {
          this.rssi = rssi;
     }

     public float getSnr()
     {
          return snr;
     }

     public void setSnr(float snr)
     {
          this.snr = snr;
     }

     public String getEncdata()
     {
          return encdata;
     }

     public void setEncdata(String encdata)
     {
          this.encdata = encdata;
     }
}
