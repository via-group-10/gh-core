package dk.grinhouse.api.controllers;

import dk.grinhouse.api.exceptions.WrongParameterException;
import dk.grinhouse.api.services.GraphService;
import dk.grinhouse.models.Measurement;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GraphController
{
  private final GraphService graphService;

  public GraphController(GraphService graphService)
  {
    this.graphService = graphService;
  }

  @Operation(summary = "get temperature measurements for graph data", description = "")
  @GetMapping("/api/graph/temperature")
  @ResponseBody
  public List<Measurement> getTemperatureGraphData(@Param("filter") String filter)
  {
    switch (filter)
    {
      case "daily":
        return graphService.getDailyTemperatureGraphData();
      case "weekly":
        return graphService.getWeeklyTemperatureGraphData();
      case "monthly":
        return graphService.getMonthlyTemperatureGraphData();
      default:
        throw new WrongParameterException();
    }
  }

  @Operation(summary = "get humidity measurements for graph data", description = "")
  @GetMapping("/api/graph/humidity")
  @ResponseBody
  public List<Measurement> getHumidityGraphData(@Param("filter") String filter)
  {
    switch (filter)
    {
      case "daily":
        return graphService.getDailyHumidityGraphData();
      case "weekly":
        return graphService.getWeeklyHumidityGraphData();
      case "monthly":
        return graphService.getMonthlyHumidityGraphData();
      default:
        throw new WrongParameterException();
    }
  }

  @Operation(summary = "get carbon dioxide measurements for graph data", description = "")
  @GetMapping("/api/graph/carbon-dioxide")
  @ResponseBody
  public List<Measurement> getCarbonDioxideGraphData(@Param("filter") String filter)
  {
    switch (filter)
    {
      case "daily":
        return graphService.getDailyCarbonDioxideGraphData();
      case "weekly":
        return graphService.getWeeklyCarbonDioxideGraphData();
      case "monthly":
        return graphService.getMonthlyCarbonDioxideGraphData();
      default:
        throw new WrongParameterException();
    }
  }

}
