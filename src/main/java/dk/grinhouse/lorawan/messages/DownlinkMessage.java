package dk.grinhouse.lorawan.messages;

import org.json.JSONObject;

import java.util.HashMap;

public class DownlinkMessage
{
     private String cmd;
     private String EUI;
     private int port;
     private String data;

     public DownlinkMessage()
     {
     }

     public DownlinkMessage(String EUI, int port, String data)
     {
          this.cmd = "tx";
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

     public JSONObject getJson()
     {
          HashMap<String, Object> downlinkMsg = new HashMap<>();
          downlinkMsg.put("cmd", cmd);
          downlinkMsg.put("EUI", EUI);
          downlinkMsg.put("port", port);
          downlinkMsg.put("data", data);
          return new JSONObject(downlinkMsg);
     }
}
