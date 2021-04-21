package dk.grinhouse.api;

import dk.grinhouse.api.services.IMeasurementService;
import dk.grinhouse.api.services.InMemoryMeasurementService;
import dk.grinhouse.api.services.MeasurementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig
{
  @Bean("measurementService")
  public IMeasurementService getMeasurementService(){
    return new InMemoryMeasurementService();
//    return new MeasurementService();
  }


}
