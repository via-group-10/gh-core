package dk.grinhouse.api.exceptions;

public class InvalidGreenhouseIDException extends RuntimeException
{
  public InvalidGreenhouseIDException()
  {
    super("Wrong Greenhouse ID entered");
  }
}
