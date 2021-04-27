package dk.grinhouse.api.services;

import dk.grinhouse.persistence.repositories.IMeasurementRepository;
import dk.grinhouse.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

	public List<Measurement> getAllMeasurements()
	{
		return measurementRepository.findAll();
	}

	public List<Measurement> getLatestMeasurements()
	{
		return measurementRepository.getLatestMeasurements();
	}

}
