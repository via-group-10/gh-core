package dk.grinhouse.api.services;

import dk.grinhouse.persistence.repositories.IGreenhouseRepository;
import dk.grinhouse.models.Greenhouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
