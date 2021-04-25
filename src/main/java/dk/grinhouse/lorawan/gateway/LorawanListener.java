package dk.grinhouse.lorawan.gateway;

import java.util.EventListener;
import java.util.Objects;

public interface LorawanListener extends EventListener
{
     void update(Object obj);
}
