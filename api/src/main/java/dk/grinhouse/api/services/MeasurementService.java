package dk.grinhouse.api.services;

import models.Measurement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class MeasurementService implements IMeasurementService
{
  @Override public ArrayList<Measurement> getLatestMeasurements()
  {
    return null;
  }
}
