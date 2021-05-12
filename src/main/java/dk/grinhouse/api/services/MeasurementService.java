package dk.grinhouse.api.services;

import dk.grinhouse.api.exceptions.NoMeasurementsInDateRangeException;
import dk.grinhouse.models.MeasurementTypeEnum;
import dk.grinhouse.persistence.repositories.IMeasurementRepository;
import dk.grinhouse.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
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

	public List<Measurement> getAllMeasurements()
	{
		return measurementRepository.findAll();
	}

	public List<Measurement> getLatestMeasurements()
	{
		return measurementRepository.getLatestMeasurements();
	}

	public List<Measurement> getTemperatureMeasurements()
	{
		return measurementRepository.findByMeasurementTypeEnum(MeasurementTypeEnum.temperature);
	}

	public List<Measurement> getHumidityMeasurements()
	{
		return measurementRepository.findByMeasurementTypeEnum(MeasurementTypeEnum.humidity);
	}

	public List<Measurement> getCarbonDioxideMeasurements()
	{
		return measurementRepository.findByMeasurementTypeEnum(MeasurementTypeEnum.carbonDioxide);
	}

	public List<Measurement> getTemperatureWithinDate(Timestamp from, Timestamp to)
	{
		List<Measurement> measurements = measurementRepository.getTemperatureWithinDate(from.toString(), to.toString());

		if(measurements.isEmpty())
		{
			throw new NoMeasurementsInDateRangeException();
		}

		return measurements;
	}

	public List<Measurement> getHumidityWithinDate(Timestamp from, Timestamp to)
	{
		List<Measurement> measurements = measurementRepository.getHumidityWithinDate(from.toString(), to.toString());

		if(measurements.isEmpty())
		{
			throw new NoMeasurementsInDateRangeException();
		}

		return measurements;
	}

	public List<Measurement> getCarbonDioxideWithinDate(Timestamp from, Timestamp to)
	{
		List<Measurement> measurements = measurementRepository.getCarbonDioxideWithinDate(from.toString(), to.toString());

		if(measurements.isEmpty())
		{
			throw new NoMeasurementsInDateRangeException();
		}

		return measurements;
	}
}
