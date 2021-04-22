package dk.grinhouse.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"dk.grinhouse.core"})
@SpringBootApplication
public class ApiApplication
{
     public static void main(String[] args)
     {
          SpringApplication.run(ApiApplication.class, args);
     }
}
