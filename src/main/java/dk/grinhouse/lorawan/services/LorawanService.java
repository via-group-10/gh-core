package dk.grinhouse.lorawan.services;

import dk.grinhouse.lorawan.messages.DownlinkMessage;
import dk.grinhouse.lorawan.messages.UplinkMessage;

public interface LorawanService
{
     DownlinkMessage getDownlinkMessageCache();
     void handleUplinkMessage(UplinkMessage uplinkMessage);
}
