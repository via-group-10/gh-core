package dk.grinhouse.api.services;

import dk.grinhouse.models.ACState;
import dk.grinhouse.models.CarbonDioxideGeneratorState;
import dk.grinhouse.models.HumidifierState;
import dk.grinhouse.persistence.repositories.IACStateRepository;
import dk.grinhouse.persistence.repositories.ICarbonDioxideGeneratorStateRepository;
import dk.grinhouse.persistence.repositories.IHumidifierStateRepository;
import org.springframework.stereotype.Component;

@Component
public class DeviceStateServiceImpl implements DeviceStateService
{
     private IACStateRepository acStateRepository;
     private ICarbonDioxideGeneratorStateRepository co2GenStateRepository;
     private IHumidifierStateRepository humidifierStateRepository;

     public DeviceStateServiceImpl(IACStateRepository acStateRepository,
          ICarbonDioxideGeneratorStateRepository carbonDioxideGeneratorStateRepository,
          IHumidifierStateRepository humidifierStateRepository)
     {
          this.acStateRepository = acStateRepository;
          this.co2GenStateRepository = carbonDioxideGeneratorStateRepository;
          this.humidifierStateRepository = humidifierStateRepository;
     }

     @Override
     public ACState getLatestAcState()
     {
          return acStateRepository.getLatestACState();
     }

     @Override
     public CarbonDioxideGeneratorState getLatestCarbonDioxideGeneratorState()
     {
          return co2GenStateRepository.getLatestCarbonDioxideGeneratorState();
     }

     @Override
     public HumidifierState getLatestHumidifierState()
     {
          return humidifierStateRepository.getLatestHumidifierState();
     }
}
