package dk.grinhouse.lorawan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LorawanWebSocketConfiguration
{
     @Bean("url")
     public String getUrl()
     {
          return "wss://iotnet.cibicom.dk/app?token=vnoTvAAAABFpb3RuZXQuY2liaWNvbS5kaw-oHDeszgxTPQp6j_47w7M=";
     }

     @Bean("EUI")
     public String getEui()
     {
          return "0004A30B00259F36";
     }
}
