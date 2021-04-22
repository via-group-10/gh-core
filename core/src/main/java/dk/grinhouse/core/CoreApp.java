package dk.grinhouse.core;

import dk.grinhouse.core.service.GreenhouseService;
import dk.grinhouse.core.service.MeasurementService;
import dk.grinhouse.core.shared.model.Greenhouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class CoreApp
{
	public static void main(String[] args)
	{
		SpringApplication.run(CoreApp.class, args);
	}
}
