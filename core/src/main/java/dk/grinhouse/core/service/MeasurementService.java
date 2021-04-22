package dk.grinhouse.core.service;

import dk.grinhouse.core.repository.IMeasurementRepository;
import dk.grinhouse.core.shared.model.Measurement;
import dk.grinhouse.core.shared.model.MeasurementTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
