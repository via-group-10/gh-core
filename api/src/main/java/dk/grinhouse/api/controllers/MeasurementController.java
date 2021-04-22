package dk.grinhouse.api.controllers;

import dk.grinhouse.api.services.IMeasurementService;
import models.Measurement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;

@RestController
public class MeasurementController
{
  private final IMeasurementService measurementService;

  public MeasurementController(IMeasurementService measurementService)
  {
    this.measurementService = measurementService;
  }

  @GetMapping("/measurements")
  @ResponseBody
  public ResponseEntity<ArrayList<Measurement>> getLatestMeasurements()
  {
    var measurements = measurementService.getLatestMeasurements();
    return ResponseEntity.created(URI.create("http://localhost:8080/measurements")).body(measurements);
  }
}
