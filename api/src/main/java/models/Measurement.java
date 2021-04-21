package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Measurement implements Serializable
{
  private float value;
  private String unit;
  private LocalDateTime measurementDateTime;
  private MeasurementType type;

  public Measurement(float value, String unit, LocalDateTime measurementDateTime,
      MeasurementType type)
  {
    this.value = value;
    this.unit = unit;
    this.measurementDateTime = measurementDateTime;
    this.type = type;
  }

  public void setValue(float value)
  {
    this.value = value;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public void setMeasurementDateTime(LocalDateTime measurementDateTime)
  {
    this.measurementDateTime = measurementDateTime;
  }

  public void setType(MeasurementType type)
  {
    this.type = type;
  }

  public float getValue()
  {
    return value;
  }

  public String getUnit()
  {
    return unit;
  }

  public LocalDateTime getMeasurementDateTime()
  {
    return measurementDateTime;
  }

  public MeasurementType getType()
  {
    return type;
  }
}
