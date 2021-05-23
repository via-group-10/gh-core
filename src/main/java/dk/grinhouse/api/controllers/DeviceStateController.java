package dk.grinhouse.api.controllers;

import dk.grinhouse.api.services.DeviceStateService;
import dk.grinhouse.models.ACState;
import dk.grinhouse.models.CarbonDioxideGeneratorState;
import dk.grinhouse.models.HumidifierState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceStateController
{
     private DeviceStateService deviceStateService;

     public DeviceStateController(DeviceStateService deviceStateService)
     {
          this.deviceStateService = deviceStateService;
     }

     @GetMapping("/api/device-state/ac/latest")
     public ResponseEntity<ACState> getLatestAcState()
     {
          ACState acState = deviceStateService.getLatestAcState();
          if (acState == null)
               return ResponseEntity.notFound().build();
          else
               return ResponseEntity.ok(acState);
     }

     @GetMapping("/api/device-state/co2gen/latest")
     public ResponseEntity<CarbonDioxideGeneratorState> getLatestCarbonDioxideGeneratorState()
     {
          CarbonDioxideGeneratorState co2State = deviceStateService.getLatestCarbonDioxideGeneratorState();
          if (co2State == null)
               return ResponseEntity.notFound().build();
          else
               return ResponseEntity.ok(co2State);
     }

     @GetMapping("/api/device-state/humidifier/latest")
     public ResponseEntity<HumidifierState> getLatestHumidifierState()
     {
          HumidifierState humidifierState = deviceStateService.getLatestHumidifierState();
          if (humidifierState == null)
               return ResponseEntity.notFound().build();
          else
               return ResponseEntity.ok(humidifierState);
     }
}
