package dk.grinhouse.api.exceptions;

public class WrongParameterException extends RuntimeException
{
  public WrongParameterException()
  {
    super("You entered a wrong parameter for this request");
  }
}
