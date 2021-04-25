package dk.grinhouse.lorawan.services;

import dk.grinhouse.lorawan.MeasurementBatch;
import dk.grinhouse.lorawan.gateway.LorawanListener;
import dk.grinhouse.models.Measurement;
import dk.grinhouse.models.MeasurementTypeEnum;
import dk.grinhouse.persistence.repositories.IMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LorawanService implements LorawanListener
{
     private IMeasurementRepository measurementRepository;

     @Autowired
     public LorawanService(IMeasurementRepository measurementRepository)
     {
          this.measurementRepository = measurementRepository;
     }

     @Override
     public void update(Object obj)
     {
          if (obj instanceof MeasurementBatch) {
               newMeasurementBatch((MeasurementBatch) obj);
          }
     }

     private void newMeasurementBatch(MeasurementBatch batch)
     {
          System.out.println("Temperature: " + batch.getTemperature());
          System.out.println("Humidity: " + batch.getHumidity());
          System.out.println("Carbon Dioxide: " + batch.getCarbonDioxideLevel());

          Measurement tempMeasurement = getTemperature(batch);
          Measurement humMeasurement = getHumidity(batch);
          Measurement co2Measurement = getCarbonDioxdeLevel(batch);

          measurementRepository.save(tempMeasurement);
          measurementRepository.save(humMeasurement);
          measurementRepository.save(co2Measurement);
     }

     private Measurement getHumidity(MeasurementBatch batch)
     {
          Measurement humidity = new Measurement();
          humidity.setGreenhouseId(1);
          humidity.setMeasurementDateTime(Timestamp.valueOf(LocalDateTime.now()));
          humidity.setMeasurementTypeEnum(MeasurementTypeEnum.humidity);
          humidity.setMeasurementValue(batch.getHumidity());
          return humidity;
     }

     private Measurement getCarbonDioxdeLevel(MeasurementBatch batch)
     {
          Measurement co2level = new Measurement();
          co2level.setGreenhouseId(1);
          co2level.setMeasurementDateTime(Timestamp.valueOf(LocalDateTime.now()));
          co2level.setMeasurementTypeEnum(MeasurementTypeEnum.carbonDioxide);
          co2level.setMeasurementValue(batch.getCarbonDioxideLevel());
          return co2level;
     }

     private Measurement getTemperature(MeasurementBatch batch)
     {
          Measurement tempMeasurement = new Measurement();
          tempMeasurement.setGreenhouseId(1);
          tempMeasurement.setMeasurementDateTime(Timestamp.valueOf(LocalDateTime.now()));
          tempMeasurement.setMeasurementTypeEnum(MeasurementTypeEnum.temperature);
          tempMeasurement.setMeasurementValue(batch.getTemperature());
          return tempMeasurement;
     }
}
