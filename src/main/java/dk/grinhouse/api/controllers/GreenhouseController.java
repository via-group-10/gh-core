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

  @Operation(summary = "login | provide credentials to get greenhouse information", description = "returns information about greenhouse such as id and name")
  @PostMapping("/api/greenhouse")
  public Greenhouse login(@RequestBody Credentials credentials)
  {
    return greenhouseService.getGreenhouse(credentials);
  }

  @Operation(summary = "[debug only] allows you to create a greenhouse", description = "greenhouse can be created using this endpoint for debug purposes")
  @PutMapping("/api/greenhouse")
  public void create(@RequestBody Greenhouse greenhouse)
  {
    greenhouseService.createGreenhouse(greenhouse);
  }

  @Operation(summary = "[debug only] get list of all the greenhouses in the database", description = "returns a list of all greenhouses")
  @GetMapping("/api/greenhouse")
  public List<Greenhouse> getAll()
  {
    return greenhouseService.getAllGreenhouses();
  }
}
