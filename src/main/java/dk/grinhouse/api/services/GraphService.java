package dk.grinhouse.api.services;

import dk.grinhouse.models.Measurement;
import dk.grinhouse.persistence.repositories.IGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphService
{
  private final IGraphRepository graphRepository;

  @Autowired
  public GraphService(IGraphRepository graphRepository)
  {
    this.graphRepository = graphRepository;
  }

  public List<Measurement> getDailyTemperatureGraphData()
  {
    return graphRepository.getDailyTemperatureMeasurements();
  }

  public List<Measurement> getWeeklyTemperatureGraphData()
  {
    return graphRepository.getWeeklyTemperatureMeasurements();
  }

  public List<Measurement> getMonthlyTemperatureGraphData()
  {
    return graphRepository.getMonthlyTemperatureeasurements();
  }

  public List<Measurement> getDailyHumidityGraphData()
  {
    return graphRepository.getDailyHumidityMeasurements();
  }

  public List<Measurement> getWeeklyHumidityGraphData()
  {
    return graphRepository.getWeeklyHumidityMeasurements();
  }

  public List<Measurement> getMonthlyHumidityGraphData()
  {
    return graphRepository.getMonthlyHumidityMeasurements();
  }

  public List<Measurement> getDailyCarbonDioxideGraphData()
  {
    return graphRepository.getDailyCarbonDioxideMeasurements();
  }

  public List<Measurement> getWeeklyCarbonDioxideGraphData()
  {
    return graphRepository.getWeeklyCarbonDioxideMeasurements();
  }

  public List<Measurement> getMonthlyCarbonDioxideGraphData()
  {
    return graphRepository.getMonthlyCarbonDioxideMeasurements();
  }

}
