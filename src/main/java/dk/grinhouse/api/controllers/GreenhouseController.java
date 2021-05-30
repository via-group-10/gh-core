package dk.grinhouse.api.controllers;

import dk.grinhouse.api.OpenApiConfig;
import dk.grinhouse.api.services.GreenhouseService;
import dk.grinhouse.models.Credentials;
import dk.grinhouse.models.Greenhouse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = OpenApiConfig.TAG_GREENHOUSE_CONTROLLER)
public class GreenhouseController
{
  private final GreenhouseService greenhouseService;

  public GreenhouseController(GreenhouseService greenhouseService)
  {
    this.greenhouseService = greenhouseService;
  }

  @Operation(summary = "login, provide credentials to get greenhouse information", description = "returns information about greenhouse such as id and name")
  @PostMapping("/api/greenhouse")
  public Greenhouse login(@RequestBody Credentials credentials)
  {
    return greenhouseService.getGreenhouse(credentials);
  }
}
