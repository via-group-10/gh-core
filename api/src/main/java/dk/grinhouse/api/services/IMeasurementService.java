package dk.grinhouse.api.services;

import models.Measurement;

import java.util.ArrayList;

public interface IMeasurementService
{
  ArrayList<Measurement> getLatestMeasurements();
}
