package dk.grinhouse.api.services;

import dk.grinhouse.persistence.repositories.IMeasurementRepository;
import dk.grinhouse.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeasurementService
{
	private final IMeasurementRepository measurementRepository;
	@Autowired
	public MeasurementService(IMeasurementRepository measurementRepository)
	{
		this.measurementRepository = measurementRepository;
	}

	public void createMeasurements(List<Measurement> measurements)
	{
		measurementRepository.saveAll(measurements);
	}

	public List<Measurement> getAllMeasurement()
	{
		return measurementRepository.findAll();
	}

/*	@Query(value = "SELECT ALL FROM Measurement m inner join (SELECT isOfType, max(measurementDateTime) AS MaxDate FROM Measurement GROUP BY isOfType) tm on m.isOfType = tm.isOfType AND m.measurementDateTime = tm.MaxDate")
	public List<Measurement> getLatestTemperatureMeasurement()
	{}*/
}
