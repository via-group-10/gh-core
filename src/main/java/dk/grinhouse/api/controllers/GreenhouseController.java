package dk.grinhouse.api.controllers;

import dk.grinhouse.api.services.GreenhouseService;
import dk.grinhouse.models.Credentials;
import dk.grinhouse.models.Greenhouse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreenhouseController
{
  private final GreenhouseService greenhouseService;

  public GreenhouseController(GreenhouseService greenhouseService)
  {
    this.greenhouseService = greenhouseService;
  }

  @PostMapping("/api/greenhouse")
  public Greenhouse login(@RequestBody Credentials credentials)
  {
    return greenhouseService.getGreenhouse(credentials);
  }
}
