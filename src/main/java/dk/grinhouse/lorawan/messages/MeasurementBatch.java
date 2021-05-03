package dk.grinhouse.lorawan.messages;

public class MeasurementBatch
{
     private int temperature;
     private int humidity;
     private int carbonDioxideLevel;

     public MeasurementBatch(byte[] measurementsAsBytes)
     {
          this.temperature = (measurementsAsBytes[0] << 8) + measurementsAsBytes[1];
          this.humidity = (measurementsAsBytes[2] << 8) + measurementsAsBytes[3];
          this.carbonDioxideLevel = (measurementsAsBytes[4] << 8) + measurementsAsBytes[5];
     }

     public int getTemperature()
     {
          return temperature;
     }

     public void setTemperature(int temperature)
     {
          this.temperature = temperature;
     }

     public int getHumidity()
     {
          return humidity;
     }

     public void setHumidity(int humidity)
     {
          this.humidity = humidity;
     }

     public int getCarbonDioxideLevel()
     {
          return carbonDioxideLevel;
     }

     public void setCarbonDioxideLevel(int carbonDioxideLevel)
     {
          this.carbonDioxideLevel = carbonDioxideLevel;
     }
}
