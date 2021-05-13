package dk.grinhouse.lorawan.services;

import dk.grinhouse.events.EventType;
import dk.grinhouse.events.GrinhouseEvent;
import dk.grinhouse.lorawan.decoder.DataEncoder;
import dk.grinhouse.lorawan.messages.DownlinkMessage;
import dk.grinhouse.lorawan.messages.MeasurementBatch;
import dk.grinhouse.lorawan.messages.UplinkMessage;
import dk.grinhouse.models.*;
import dk.grinhouse.persistence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LorawanServiceImpl implements ApplicationListener<GrinhouseEvent>, LorawanService
{
     private final IMeasurementRepository measurementRepository;
     private final IThresholdProfileRepository thresholdProfileRepository;
     private final IHumidifierStateRepository humidifierStateRepository;
     private final IACStateRepository acStateRepository;
     private final ICarbonDioxideGeneratorStateRepository carbonDioxideGeneratorStateRepository;
     private final String EUI;
     private DownlinkMessage downlinkMessageCache;

     @Autowired
     public LorawanServiceImpl(IMeasurementRepository measurementRepository, IThresholdProfileRepository thresholdProfileRepository,
          IHumidifierStateRepository humidifierStateRepository,
          IACStateRepository acStateRepository,
          ICarbonDioxideGeneratorStateRepository carbonDioxideGeneratorStateRepository, String EUI)
     {
          this.measurementRepository = measurementRepository;
          this.thresholdProfileRepository = thresholdProfileRepository;
          this.humidifierStateRepository = humidifierStateRepository;
          this.acStateRepository = acStateRepository;
          this.carbonDioxideGeneratorStateRepository = carbonDioxideGeneratorStateRepository;
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

     @Override
     public DownlinkMessage getDownlinkMessageCache()
     {
          return downlinkMessageCache;
     }

     @Override
     public void handleUplinkMessage(UplinkMessage uplinkMessage)
     {
          if (uplinkMessage.getCmd().equals("rx")) {
               String data = uplinkMessage.getData();
               addNewMeasurementBatch(data);
          }
     }

     private void activeProfileUpdated()
     {
          var activeProfile = thresholdProfileRepository.findByActive(true);

          String hexThresholds = DataEncoder.bytesToHex(activeProfile.getThresholdsInBytes());

          downlinkMessageCache = new DownlinkMessage(EUI, 1, hexThresholds);
     }

     public void addNewMeasurementBatch(String data)
     {
          MeasurementBatch batch = new MeasurementBatch(DataEncoder.hexToBytes(data));

          System.out.println("Temperature: " + batch.getTemperature());
          System.out.println("Humidity: " + batch.getHumidity());
          System.out.println("Carbon Dioxide: " + batch.getCarbonDioxideLevel());

          Measurement tempMeasurement = getTemperature(batch);
          Measurement humMeasurement = getHumidity(batch);
          Measurement co2Measurement = getCarbonDioxdeLevel(batch);

          ACState acState = getAcState(batch);
          HumidifierState humState = getHumState(batch);
          CarbonDioxideGeneratorState co2GenState = getCo2GenState(batch);

          acStateRepository.save(acState);
          humidifierStateRepository.save(humState);
          carbonDioxideGeneratorStateRepository.save(co2GenState);
          measurementRepository.save(tempMeasurement);
          measurementRepository.save(humMeasurement);
          measurementRepository.save(co2Measurement);
     }

     private HumidifierState getHumState(MeasurementBatch batch)
     {
          HumidifierState humState = new HumidifierState();
          humState.setGreenhouseId(1);
          humState.setStateDateTime(Timestamp.valueOf(LocalDateTime.now()));

          int humStateInInt = batch.getHumState();

          if (humStateInInt == 0)
          {
               humState.setHumidifierOn(true);
               humState.setDehumidifierOn(false);
          }
          else if (humStateInInt == 1)
          {
               humState.setHumidifierOn(false);
               humState.setDehumidifierOn(true);
          }
          else //humStateInInt == -1 or other
          {
               humState.setHumidifierOn(false);
               humState.setDehumidifierOn(false);
          }

          return humState;
     }

     private CarbonDioxideGeneratorState getCo2GenState(MeasurementBatch batch)
     {
          CarbonDioxideGeneratorState co2GenState = new CarbonDioxideGeneratorState();
          co2GenState.setGreenhouseId(1);
          co2GenState.setStateDateTime(Timestamp.valueOf(LocalDateTime.now()));

          int co2GenStateInInt = batch.getCo2GenState();

          if (co2GenStateInInt == 0)
          {
               co2GenState.setCarbonDioxideGeneratorOn(true);
          }
          else //co2GenStateInInt == -1 or other
          {
               co2GenState.setCarbonDioxideGeneratorOn(false);
          }
          return co2GenState;
     }

     private ACState getAcState(MeasurementBatch batch)
     {
          ACState acState = new ACState();
          acState.setGreenhouseId(1);
          acState.setStateDateTime(Timestamp.valueOf(LocalDateTime.now()));

          int acStateInInt = batch.getAcState();

          if (acStateInInt == 0)
          {
               acState.setCoolerOn(true);
               acState.setHeaterOn(false);
          }
          else if (acStateInInt == 1)
          {
               acState.setCoolerOn(false);
               acState.setHeaterOn(true);
          }
          else  //acStateInInt == -1 or other
          {
               acState.setCoolerOn(false);
               acState.setHeaterOn(false);
          }

          return acState;
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
