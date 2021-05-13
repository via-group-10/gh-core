package dk.grinhouse.api.controllers;

import dk.grinhouse.api.OpenApiConfig;
import dk.grinhouse.api.services.MeasurementService;
import dk.grinhouse.models.Measurement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@Tag(name = OpenApiConfig.TAG_MEASUREMENT_CONTROLLER)
public class MeasurementController
{
  private final MeasurementService measurementService;

  public MeasurementController(MeasurementService measurementService)
  {
    this.measurementService = measurementService;
  }

  @Operation(summary = "get all measurements in the database", description = "")
  @GetMapping("/api/measurement")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getAllMeasurements()
  {
    var measurements = measurementService.getAllMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @Operation(summary = "returns a size three list of latest measurements one of each type", description = "")
  @GetMapping("/api/measurement/latest")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getLatestMeasurements()
  {
    var measurements = measurementService.getLatestMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @Operation(summary = "get all temperature measurements", description = "")
  @GetMapping("/api/measurement/temperature")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getTemperatureMeasurements()
  {
    var measurements = measurementService.getTemperatureMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @Operation(summary = "get all temperature measurements within date", description = "")
  @GetMapping("/api/measurement/temperature/range")
  @ResponseBody
  public List<Measurement> getTemperatureMeasurementsBetweenDates(@RequestParam
      Timestamp from, @RequestParam Timestamp to)
  {
    var measurements = measurementService.getTemperatureWithinDate(from, to);
    return measurements;
  }

  @Operation(summary = "get all humidity measurements", description = "")
  @GetMapping("/api/measurement/humidity")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getHumidityMeasurements()
  {
    var measurements = measurementService.getHumidityMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @Operation(summary = "get all humidity measurements within range", description = "")
  @GetMapping("/api/measurement/humidity/range")
  @ResponseBody
  public List<Measurement> getHumidityMeasurementsBetweenDates(@RequestParam
      Timestamp from, @RequestParam Timestamp to)
  {
    var measurements = measurementService.getHumidityWithinDate(from, to);
    return measurements;
  }

  @Operation(summary = "get all carbon dioxide measurements", description = "")
  @GetMapping("/api/measurement/carbon-dioxide")
  @ResponseBody
  public ResponseEntity<List<Measurement>> getCarbonDioxideMeasurements()
  {
    var measurements = measurementService.getCarbonDioxideMeasurements();
    return ResponseEntity.ok(measurements);
  }

  @Operation(summary = "get all carbon dioxide measurements within range", description = "")
  @GetMapping("/api/measurement/carbon-dioxide/range")
  @ResponseBody
  public List<Measurement> getCarbonDioxideMeasurementsBetweenDates(@RequestParam
      Timestamp from, @RequestParam Timestamp to)
  {
    var measurements = measurementService.getCarbonDioxideWithinDate(from, to);
    return measurements;
  }
}
