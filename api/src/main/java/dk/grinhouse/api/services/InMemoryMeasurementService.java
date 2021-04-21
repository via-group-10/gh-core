package dk.grinhouse.api.services;

import models.Measurement;
import models.MeasurementType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class InMemoryMeasurementService implements IMeasurementService
{
  private ArrayList<Measurement> savedMeasurements;

  public InMemoryMeasurementService()
  {
    savedMeasurements = new ArrayList<Measurement>();
    fillMeasurements();
  }

  public void fillMeasurements(){
    savedMeasurements.add(new Measurement(5,"Celsius",LocalDateTime.of(1999,12,11,15,54,10),
        MeasurementType.TEMPERATURE));
    savedMeasurements.add(new Measurement(3,"ppm",LocalDateTime.of(2015,8,11,15,54,10),
        MeasurementType.CARBON_DIOXIDE));
    savedMeasurements.add(new Measurement(2,"%",LocalDateTime.of(202,11,11,15,54,10),
        MeasurementType.HUMIDITY));
  }

  @Override public ArrayList<Measurement> getLatestMeasurements()
  {
    return savedMeasurements;
  }
}
