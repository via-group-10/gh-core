package dk.grinhouse.lorawan.messages;

public class DownlinkMessage
{
     private String cmd = "tx";
     private String EUI;
     private int port;
     private String data;
     private boolean confirmed = true;
     private String success = "Message enqueued";
     private String error = "Message not enqueued";

     public DownlinkMessage()
     {
     }

     public DownlinkMessage(String EUI, int port, String data)
     {
          this.EUI = EUI;
          this.port = port;
          this.data = data;
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

     public String getError()
     {
          return error;
     }

     public void setError(String error)
     {
          this.error = error;
     }

     public String getSuccess()
     {
          return success;
     }

     public void setSuccess(String success)
     {
          this.success = success;
     }

     public boolean isConfirmed()
     {
          return confirmed;
     }

     public void setConfirmed(boolean confirmed)
     {
          this.confirmed = confirmed;
     }

}
