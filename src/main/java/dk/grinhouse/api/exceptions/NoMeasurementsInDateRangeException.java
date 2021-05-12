package dk.grinhouse.api.exceptions;

public class NoMeasurementsInDateRangeException extends RuntimeException
{
  public NoMeasurementsInDateRangeException()
  {
    super("There are no measurements in given date range");
  }
}
