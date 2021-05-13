package dk.grinhouse.lorawan.services;

import dk.grinhouse.events.EventType;
import dk.grinhouse.events.GrinhouseEvent;
import dk.grinhouse.lorawan.decoder.DataEncoder;
import dk.grinhouse.lorawan.messages.DownlinkMessage;
import dk.grinhouse.lorawan.messages.MeasurementBatch;
import dk.grinhouse.lorawan.messages.UplinkMessage;
import dk.grinhouse.models.Measurement;
import dk.grinhouse.models.MeasurementTypeEnum;
import dk.grinhouse.persistence.repositories.IMeasurementRepository;
import dk.grinhouse.persistence.repositories.IThresholdProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LorawanService implements ApplicationListener<GrinhouseEvent>
{
     private final IMeasurementRepository measurementRepository;
     private final IThresholdProfileRepository thresholdProfileRepository;
     private final String EUI;
     private DownlinkMessage downlinkMessageCache;

     @Autowired
     public LorawanService(IMeasurementRepository measurementRepository, IThresholdProfileRepository thresholdProfileRepository, String EUI)
     {
          this.measurementRepository = measurementRepository;
          this.thresholdProfileRepository = thresholdProfileRepository;
          this.EUI = EUI;
     }


     @Override
     public void onApplicationEvent(GrinhouseEvent event)
     {
          if (event.getType() == EventType.PROFILE_UPDATE)
          {
               activeProfileUpdated();
          }
     }

     public DownlinkMessage getDownlinkMessageCache()
     {
          return downlinkMessageCache;
     }

     private void activeProfileUpdated()
     {
          var activeProfile = thresholdProfileRepository.findByActive(true);

          String hexThresholds = DataEncoder.bytesToHex(activeProfile.getThresholdsInBytes());

          downlinkMessageCache = new DownlinkMessage(EUI, 1, hexThresholds);
     }

     public void addNewMeasurementBatch(String data)
     {
//          MeasurementDataDecoder decoder = new MeasurementDataDecoder(data);
          MeasurementBatch batch = new MeasurementBatch(DataEncoder.hexToBytes(data));

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

     public void handleUplinkMessage(UplinkMessage uplinkMessage)
     {
          if (uplinkMessage.getCmd().equals("rx")) {
               String data = uplinkMessage.getData();
               addNewMeasurementBatch(data);
          }
     }
}
