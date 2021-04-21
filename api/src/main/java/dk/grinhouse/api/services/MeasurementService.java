package dk.grinhouse.api.services;

import models.Measurement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MeasurementService implements IMeasurementService
{
  private InMemoryMeasurementService inMemoryMeasurementService;

  public MeasurementService(InMemoryMeasurementService inMemoryMeasurementService)
  {
    this.inMemoryMeasurementService = inMemoryMeasurementService;
  }

  @Override public ArrayList<Measurement> getLatestMeasurements()
  {
    return null;
  }
}
