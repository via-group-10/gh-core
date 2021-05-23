package dk.grinhouse.api.services;

import dk.grinhouse.models.ACState;
import dk.grinhouse.models.CarbonDioxideGeneratorState;
import dk.grinhouse.models.HumidifierState;

public interface DeviceStateService
{
     ACState getLatestAcState();
     CarbonDioxideGeneratorState getLatestCarbonDioxideGeneratorState();
     HumidifierState getLatestHumidifierState();
}
