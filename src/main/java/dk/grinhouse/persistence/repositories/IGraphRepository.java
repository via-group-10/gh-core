package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGraphRepository extends JpaRepository<Measurement, Integer>
{
  //Get daily temperature
  @Query(value = "SELECT * FROM [GrinHouse].[dbo].[Measurement] WHERE [measurementDateTime] < CURRENT_TIMESTAMP and [measurementDateTime] > DATEADD(HOUR,-24,CURRENT_TIMESTAMP) and [isOfType] = 'temperature' ORDER BY [measurementDateTime] DESC ", nativeQuery = true)
  List<Measurement> getDailyTemperatureMeasurements();
  //Get weekly temperature
  @Query(value = "SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType] FROM ( SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType], ROW_NUMBER() OVER (ORDER BY [measurementId]) AS rownum FROM [GrinHouse].[dbo].[Measurement] ) AS t WHERE t.rownum % 16 = 0 and convert(DATE, [measurementDateTime]) > getdate() - 6 and convert(DATE, [measurementDateTime]) <= getdate() and [isOfType] = 'temperature' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getWeeklyTemperatureMeasurements();
  //Get monthly temperature
  @Query(value = "SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType] FROM ( SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType], ROW_NUMBER() OVER (ORDER BY [measurementId]) AS rownum FROM [GrinHouse].[dbo].[Measurement] ) AS t WHERE t.rownum % 86 = 0 and convert(DATE, [measurementDateTime]) > getdate() - 30 and convert(DATE, [measurementDateTime]) <= getdate() and [isOfType] = 'temperature' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getMonthlyTemperatureeasurements();

  //Get daily humidity
  @Query(value = "SELECT * FROM [GrinHouse].[dbo].[Measurement] WHERE [measurementDateTime] < CURRENT_TIMESTAMP and [measurementDateTime] > DATEADD(HOUR,-24,CURRENT_TIMESTAMP) and [isOfType] = 'humidity' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getDailyHumidityMeasurements();
  //Get weekly temperature
  @Query(value = "SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType] FROM ( SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType], ROW_NUMBER() OVER (ORDER BY [measurementId]) AS rownum FROM [GrinHouse].[dbo].[Measurement] ) AS t WHERE t.rownum % 16 = 0 and convert(DATE, [measurementDateTime]) > getdate() - 6 and convert(DATE, [measurementDateTime]) <= getdate() and [isOfType] = 'humidity' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getWeeklyHumidityMeasurements();
  //Get monthly temperature
  @Query(value = "SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType] FROM ( SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType], ROW_NUMBER() OVER (ORDER BY [measurementId]) AS rownum FROM [GrinHouse].[dbo].[Measurement] ) AS t WHERE t.rownum % 86 = 0 and convert(DATE, [measurementDateTime]) > getdate() - 30 and convert(DATE, [measurementDateTime]) <= getdate() and [isOfType] = 'humidity' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getMonthlyHumidityMeasurements();

  //Get daily carbon dioxide
  @Query(value = "SELECT * FROM [GrinHouse].[dbo].[Measurement] WHERE [measurementDateTime] < CURRENT_TIMESTAMP and [measurementDateTime] > DATEADD(HOUR,-24,CURRENT_TIMESTAMP) and [isOfType] = 'carbonDioxide' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getDailyCarbonDioxideMeasurements();
  //Get weekly temperature
  @Query(value = "SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType] FROM ( SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType], ROW_NUMBER() OVER (ORDER BY [measurementId]) AS rownum FROM [GrinHouse].[dbo].[Measurement] ) AS t WHERE t.rownum % 16 = 0 and convert(DATE, [measurementDateTime]) > getdate() - 6 and convert(DATE, [measurementDateTime]) <= getdate() and [isOfType] = 'carbonDioxide' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getWeeklyCarbonDioxideMeasurements();
  //Get monthly temperature
  @Query(value = "SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType] FROM ( SELECT [measurementId], [measuredValue], [measurementDateTime], [belongsTo], [isOfType], ROW_NUMBER() OVER (ORDER BY [measurementId]) AS rownum FROM [GrinHouse].[dbo].[Measurement] ) AS t WHERE t.rownum % 86 = 0 and convert(DATE, [measurementDateTime]) > getdate() - 30 and convert(DATE, [measurementDateTime]) <= getdate() and [isOfType] = 'carbonDioxide' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getMonthlyCarbonDioxideMeasurements();

}
