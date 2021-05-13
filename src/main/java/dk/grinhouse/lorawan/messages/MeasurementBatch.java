package dk.grinhouse.lorawan.messages;

public class MeasurementBatch
{
     private int temperature;
     private int humidity;
     private int carbonDioxideLevel;
     private int acState;
     private int humState;
     private int co2GenState;

     public MeasurementBatch(byte[] measurementsAsBytes)
     {
          this.temperature = (measurementsAsBytes[0] << 8) + measurementsAsBytes[1];
          this.humidity = (measurementsAsBytes[2] << 8) + measurementsAsBytes[3];
          this.carbonDioxideLevel = (measurementsAsBytes[4] << 8) + measurementsAsBytes[5];
          this.acState = (measurementsAsBytes[6] << 8) + measurementsAsBytes[7];
          this.humState = (measurementsAsBytes[8] << 8) + measurementsAsBytes[9];
          this.co2GenState = (measurementsAsBytes[10] << 8) + measurementsAsBytes[11];
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

     public int getAcState()
     {
          return acState;
     }

     public void setAcState(int acState)
     {
          this.acState = acState;
     }

     public int getHumState()
     {
          return humState;
     }

     public void setHumState(int humState)
     {
          this.humState = humState;
     }

     public int getCo2GenState()
     {
          return co2GenState;
     }

     public void setCo2GenState(int co2GenState)
     {
          this.co2GenState = co2GenState;
     }
}
