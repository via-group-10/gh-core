package dk.grinhouse.api.exceptions;

public class InvalidProfileId extends RuntimeException
{
  public InvalidProfileId()
  {
    super("Wrong thresholdProfileId entered");
  }
}
