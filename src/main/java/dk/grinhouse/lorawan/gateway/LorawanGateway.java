package dk.grinhouse.lorawan.gateway;

import dk.grinhouse.lorawan.services.LorawanService;
import org.springframework.stereotype.Service;

@Service
public class LorawanGateway implements Runnable
{
     private final LorawanService lorawanService;

     public LorawanGateway(LorawanService lorawanService)
     {
          this.lorawanService = lorawanService;
          new Thread(this).start();
     }

     @Override
     public void run()
     {
          WebsocketClient wsc = new WebsocketClient(
               "wss://iotnet.cibicom.dk/app?token=vnoTvAAAABFpb3RuZXQuY2liaWNvbS5kaw-oHDeszgxTPQp6j_47w7M="
          );
          wsc.registerListener(lorawanService);
     }
}
