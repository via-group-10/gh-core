package dk.grinhouse.ghcore;

import dk.grinhouse.models.Measurement;
import dk.grinhouse.models.MeasurementTypeEnum;
import dk.grinhouse.persistence.repositories.IMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Random;

@SpringBootTest
public class TestDataLoader
{
  @Autowired
  private  IMeasurementRepository measurementRepository;
  private Random random = new Random();

  @Test
  public void add9mMeasurements() throws InterruptedException
  {
    long millis = System.currentTimeMillis();

    for (int i = 1; i <= 12; i++)
    {
      measurementRepository.save(new Measurement(randFloat(20f, 30f), new Timestamp(millis - (12345*i)), 1, MeasurementTypeEnum.temperature));
      measurementRepository.save(new Measurement(randFloat(20f, 30f), new Timestamp(millis - (23145*i)), 1, MeasurementTypeEnum.carbonDioxide));
      measurementRepository.save(new Measurement(randFloat(20f, 30f), new Timestamp(millis - (32345*i)), 1, MeasurementTypeEnum.humidity));
    }
  }

  @Test
  public void removeAllMeasurements(){
    measurementRepository.deleteAll();
  }

  public static float randFloat(float min, float max) {
    Random rand = new Random();
    return rand.nextFloat() * (max - min) + min;
  }
}
