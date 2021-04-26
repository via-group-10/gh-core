package dk.grinhouse.api.controllers;

import dk.grinhouse.api.services.MeasurementService;
import dk.grinhouse.models.Measurement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class MeasurementController
{
  private final MeasurementService measurementService;

  public MeasurementController(MeasurementService measurementService)
  {
    this.measurementService = measurementService;
  }

  @GetMapping("/measurements")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getLatestMeasurements()
  {
    var measurements = measurementService.getAllMeasurement();
    return ResponseEntity.created(URI.create("http://localhost:8080/measurements")).body(measurements);
  }
}
