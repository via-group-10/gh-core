package dk.grinhouse.api.exceptions;

public class InvalidCredentialsException extends RuntimeException
{
  public InvalidCredentialsException()
  {
    super("Wrong username or password");
  }
}
