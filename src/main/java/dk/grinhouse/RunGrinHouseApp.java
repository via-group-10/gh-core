package dk.grinhouse;

import dk.grinhouse.lorawan.gateway.LorawanGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunGrinHouseApp
{
     public static void main(String[] args)
     {
          SpringApplication.run(RunGrinHouseApp.class, args);
     }
}
