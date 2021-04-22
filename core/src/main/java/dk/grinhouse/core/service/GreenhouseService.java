package dk.grinhouse.core.service;

import dk.grinhouse.core.repository.IGreenhouseRepository;
import dk.grinhouse.core.shared.model.Greenhouse;
import dk.grinhouse.core.shared.model.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class GreenhouseService
{
	private final IGreenhouseRepository greenhouseRepository;

	@Autowired
	public GreenhouseService(IGreenhouseRepository greenhouseRepository)
	{
		this.greenhouseRepository = greenhouseRepository;
	}

	public void createGreenhouse(Greenhouse greenhouse)
	{
		greenhouseRepository.save(greenhouse);
	}

	public void getGreenhouse()
	{
		greenhouseRepository.findAll();
	}
}
