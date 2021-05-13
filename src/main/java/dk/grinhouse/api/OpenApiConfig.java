package dk.grinhouse.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  public static final String TAG_PROFILE_CONTROLLER = "Threshold Profiles";
  public static final String TAG_GREENHOUSE_CONTROLLER = "Greenhouses";
  public static final String TAG_MEASUREMENT_CONTROLLER = "Measurements";


  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("GrinHouse Application API")
        .description("This is a RESTful service to be used by the Grinhouse android app.")
        .version("2.2.0"));
  }
}
