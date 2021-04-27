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

  @GetMapping("/api/measurement")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getAllMeasurements()
  {
    var measurements = measurementService.getAllMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @GetMapping("/api/measurement/latest")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getLatestMeasurements()
  {
    var measurements = measurementService.getLatestMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @GetMapping("/api/measurement/temperature")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getTemperatureMeasurements()
  {
    var measurements = measurementService.getTemperatureMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @GetMapping("/api/measurement/humidity")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getHumidityMeasurements()
  {
    var measurements = measurementService.getHumidityMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @GetMapping("/api/measurement/carbon-dioxide")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getCarbonDioxideMeasurements()
  {
    var measurements = measurementService.getCarbonDioxideMeasurements();
    return ResponseEntity.ok(measurements);
  }
}
