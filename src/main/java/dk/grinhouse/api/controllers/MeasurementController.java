package dk.grinhouse.api.controllers;

import dk.grinhouse.api.OpenApiConfig;
import dk.grinhouse.api.services.MeasurementService;
import dk.grinhouse.models.Measurement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<List<Measurement>> getTemperatureMeasurements(@RequestParam(name = "top", required = false) Integer amount)
  {
    List<Measurement> measurements;
    if  (amount == null) {
      measurements = measurementService.getTemperatureMeasurements();
    }
    else {
      measurements = measurementService.getTemperatureMeasurements(amount);
    }
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
  public ResponseEntity<List<Measurement>> getHumidityMeasurements(@RequestParam(name = "top", required = false) Integer amount)
  {
    List<Measurement> measurements;
    if  (amount == null) {
      measurements = measurementService.getHumidityMeasurements();
    }
    else {
      measurements = measurementService.getHumidityMeasurements(amount);
    }
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
  public ResponseEntity<List<Measurement>> getCarbonDioxideMeasurements(@RequestParam(name = "top", required = false) Integer amount)
  {
    List<Measurement> measurements;
    if  (amount == null) {
      measurements = measurementService.getCarbonDioxideMeasurements();
    }
    else {
      measurements = measurementService.getCarbonDioxideMeasurements(amount);
    }
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
