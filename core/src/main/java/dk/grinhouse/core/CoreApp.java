package dk.grinhouse.core;

import dk.grinhouse.core.service.GreenhouseService;
import dk.grinhouse.core.service.MeasurementService;
import dk.grinhouse.core.shared.model.Greenhouse;
import dk.grinhouse.core.shared.model.Measurement;
import dk.grinhouse.core.shared.model.MeasurementTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@SpringBootApplication
public class CoreApp implements CommandLineRunner
{
	@Autowired
	private GreenhouseService greenhouseService;

	@Autowired
	private MeasurementService measurementService;

	public static void main(String[] args)
	{
		SpringApplication.run(CoreApp.class, args);
	}

	@Override
	public void run(String... args) {
//		greenhouseService.createGreenhouse(new Greenhouse("Greeen house 1", "gr1", "1234"));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Measurement measurement = new Measurement(14.23f, timestamp, 1, MeasurementTypeEnum.temperature);
		ArrayList<Measurement> measurements = new ArrayList<>();
		measurements.add(measurement);
		measurements.add(measurement);
		measurements.add(measurement);
		measurementService.createMeasurements(measurements);
	}
}
