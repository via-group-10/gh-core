package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.Measurement;
import dk.grinhouse.models.MeasurementTypeEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMeasurementRepository extends JpaRepository<Measurement, Integer>
{
  @Query(value = "select measurementId, measuredValue, measurementDateTime, belongsTo, a.isOfType from GrinHouse.dbo.Measurement a inner join ( select isOfType, max(measurementDateTime) as MaxDate from GrinHouse.dbo.Measurement group by isOfType ) b on a.isOfType = b.isOfType and a.measurementDateTime = b.MaxDate", nativeQuery = true)
  List<Measurement> getLatestMeasurements();

  List<Measurement> findByMeasurementTypeEnum(MeasurementTypeEnum measurementTypeEnum);

  List<Measurement> findByMeasurementTypeEnumOrderByMeasurementDateTimeDesc(MeasurementTypeEnum measurementTypeEnum, Pageable pageable);

  @Query(value = "SELECT * FROM [GrinHouse].[dbo].[Measurement] WHERE [measurementDateTime] >= CONVERT(DATE,:from) and [measurementDateTime] <= CONVERT(DATE,:to ) and [isOfType] = 'temperature' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getTemperatureWithinDate(@Param("from") String from,@Param("to") String to);

  @Query(value = "SELECT * FROM [GrinHouse].[dbo].[Measurement] WHERE [measurementDateTime] >= CONVERT(DATE,:from) and [measurementDateTime] <= CONVERT(DATE,:to ) and [isOfType] = 'humidity' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getHumidityWithinDate(@Param("from") String from,@Param("to") String to);

  @Query(value = "SELECT * FROM [GrinHouse].[dbo].[Measurement] WHERE [measurementDateTime] >= CONVERT(DATE,:from) and [measurementDateTime] <= CONVERT(DATE,:to ) and [isOfType] = 'carbonDioxide' ORDER BY [measurementDateTime] DESC", nativeQuery = true)
  List<Measurement> getCarbonDioxideWithinDate(@Param("from") String from,@Param("to") String to);
}